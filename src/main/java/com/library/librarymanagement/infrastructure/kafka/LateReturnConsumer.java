package com.library.librarymanagement.infrastructure.kafka;

import com.library.librarymanagement.domain.event.BookReturnedLateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class LateReturnConsumer {

    private static final Logger log = LoggerFactory.getLogger(LateReturnConsumer.class);

    @KafkaListener(topics = "library.loans.late_returns", groupId = "library_group")
    public void handleLateReturn(BookReturnedLateEvent event) {
        log.info("Received BookReturnedLateEvent: {}", event);
    }
}