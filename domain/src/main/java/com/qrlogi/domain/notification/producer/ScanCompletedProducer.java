package com.qrlogi.domain.notification.producer;

import com.qrlogi.domain.notification.event.ScanCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ScanCompletedProducer {

    private final KafkaTemplate<String, ScanCompletedEvent> kafkaTemplate;

    public void send(ScanCompletedEvent scanCompletedEvent) {

        kafkaTemplate.send("scan.completed", scanCompletedEvent);
        log.info("[kafka] scan.completed EVENT sent : {}", scanCompletedEvent);
    }

}
