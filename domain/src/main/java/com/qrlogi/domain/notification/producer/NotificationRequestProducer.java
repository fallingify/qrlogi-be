package com.qrlogi.domain.notification.producer;

import com.qrlogi.domain.notification.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * NotificationRequest용 Kafka Producer
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationRequestProducer {

    private final KafkaTemplate<String, NotificationRequest> kafkaTemplate;

    public void send(NotificationRequest notificationRequest) {
        kafkaTemplate.send("notification-requests", notificationRequest);
        log.info("[kafka] notification.email sent: {}", notificationRequest);
    }

}
