package com.library.librarymanagement.application;

import com.library.librarymanagement.domain.Loan;
import com.library.librarymanagement.domain.LoanRepositoryPort;
import com.library.librarymanagement.domain.event.BookReturnedLateEvent;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { "library.loans.late_returns" }, brokerProperties = { "listeners=PLAINTEXT://localhost:9094", "port=9094" })
@TestPropertySource(properties = { "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}" })
@ExtendWith(MockitoExtension.class)
class ReturnLoanUseCaseTest {

    @Mock
    private LoanRepositoryPort loanRepository;

    @Autowired
    private KafkaTemplate<String, BookReturnedLateEvent> kafkaTemplate;

    private ReturnLoanUseCase returnLoanUseCase;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    private BlockingQueue<ConsumerRecord<String, BookReturnedLateEvent>> records;
    private KafkaMessageListenerContainer<String, BookReturnedLateEvent> container;

    @BeforeEach
    void setUp() {
        returnLoanUseCase = new ReturnLoanUseCase(loanRepository, kafkaTemplate);

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        JsonDeserializer<BookReturnedLateEvent> valueDeserializer = new JsonDeserializer<>(BookReturnedLateEvent.class);
        valueDeserializer.addTrustedPackages("com.library.librarymanagement.domain.event");

        DefaultKafkaConsumerFactory<String, BookReturnedLateEvent> cf =
                new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(), valueDeserializer);

        ContainerProperties containerProperties = new ContainerProperties("library.loans.late_returns");
        container = new KafkaMessageListenerContainer<>(cf, containerProperties);
        records = new LinkedBlockingQueue<>();
        container.setupMessageListener((MessageListener<String, BookReturnedLateEvent>) records::add);
        container.start();

        ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());
    }

    @AfterEach
    void tearDown() {
        if (container != null) {
            container.stop();
        }
    }

    @Test
    void execute_publishesEvent_whenBookIsReturnedLate() throws InterruptedException {
        // 1. Arrange
        String loanId = "loan-123";
        String memberId = "member-abc";
        String bookId = "book-xyz";
        LocalDate dueDate = LocalDate.now().minusDays(3); // Due date was 3 days ago
        Loan lateLoan = new Loan();
        lateLoan.setLoanId(loanId);
        lateLoan.setDueDate(dueDate);
        lateLoan.setMemberId(memberId);
        lateLoan.setBookId(bookId);

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(lateLoan));
        when(loanRepository.save(any(Loan.class))).thenAnswer(inv -> inv.getArgument(0));

        // 2. Act
        returnLoanUseCase.execute(loanId);

        // 3. Assert - Check if a message arrived in Kafka
        ConsumerRecord<String, BookReturnedLateEvent> singleRecord = records.poll(10, TimeUnit.SECONDS); // Wait max 10 seg

        assertNotNull(singleRecord, "Kafka message should have been received");
        assertEquals(loanId, singleRecord.key());
        BookReturnedLateEvent event = singleRecord.value();
        assertEquals(loanId, event.loanId());
        assertEquals(memberId, event.memberId());
        assertEquals(bookId, event.bookId());
        assertEquals(dueDate, event.dueDate());
        assertTrue(event.returnDate().isAfter(dueDate));
    }

    @Test
    void execute_doesNotPublishEvent_whenBookIsNotLate() throws InterruptedException {
        // 1. Arrange
        String loanId = "loan-456";
        LocalDate dueDate = LocalDate.now().plusDays(3); // Due date is in 3 days
        Loan onTimeLoan = new Loan();
        onTimeLoan.setLoanId(loanId);
        onTimeLoan.setDueDate(dueDate);

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(onTimeLoan));
        when(loanRepository.save(any(Loan.class))).thenAnswer(inv -> inv.getArgument(0));

        // 2. Act
        returnLoanUseCase.execute(loanId);

        // 3. Assert - Check that NO message arrived in Kafka
        ConsumerRecord<String, BookReturnedLateEvent> singleRecord = records.poll(1, TimeUnit.SECONDS); // Wait only 1 seg

        assertNull(singleRecord, "Kafka message should NOT have been received for on-time return");
    }
}