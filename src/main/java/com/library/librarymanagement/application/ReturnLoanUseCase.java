package com.library.librarymanagement.application;

import com.library.librarymanagement.domain.Loan;
import com.library.librarymanagement.domain.LoanRepositoryPort;
import com.library.librarymanagement.domain.event.BookReturnedLateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReturnLoanUseCase {

    private final LoanRepositoryPort loanRepository;
    private final KafkaTemplate<String, BookReturnedLateEvent> kafkaTemplate;

    private static final Logger log = LoggerFactory.getLogger(ReturnLoanUseCase.class);

    public ReturnLoanUseCase(LoanRepositoryPort loanRepository,
                             KafkaTemplate<String, BookReturnedLateEvent> kafkaTemplate) {
        this.loanRepository = loanRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Loan execute(String loanId) {
        Loan existingLoan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (existingLoan.getReturnDate() != null) {
            log.warn("Attempted to return an already returned loan: {}", loanId);
            return existingLoan;
        }


        LocalDate returnDate = LocalDate.now();

        if (returnDate.isAfter(existingLoan.getDueDate())) {
            BookReturnedLateEvent event = new BookReturnedLateEvent(
                    existingLoan.getLoanId(),
                    existingLoan.getMemberId(),
                    existingLoan.getBookId(),
                    existingLoan.getDueDate(),
                    returnDate
            );

            String topic = "library.loans.late_returns";

            kafkaTemplate.send(topic, existingLoan.getLoanId(), event);
            log.info("Published BookReturnedLateEvent to Kafka topic {}: {}", topic, event);
        }

        existingLoan.setReturnDate(LocalDate.now());

        return loanRepository.save(existingLoan);
    }
}
