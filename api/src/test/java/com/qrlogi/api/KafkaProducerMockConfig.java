package com.qrlogi.api;

import com.qrlogi.domain.notification.dto.NotificationRequest;
import com.qrlogi.domain.notification.event.ScanCompletedEvent;
import com.qrlogi.domain.notification.producer.NotificationRequestProducer;
import com.qrlogi.domain.notification.producer.ScanCompletedProducer;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;

@TestConfiguration
public class KafkaProducerMockConfig {
    @Bean
    @Primary
    public ScanCompletedProducer scanCompletedProducerMock() {
        return Mockito.mock(ScanCompletedProducer.class);
    }

    @Bean
    @Primary
    public NotificationRequestProducer notificationRequestProducerMock() {
        return Mockito.mock(NotificationRequestProducer.class);
    }


    @SuppressWarnings("unchecked")
    @Bean
    @Primary
    public KafkaTemplate<String, ScanCompletedEvent> scanCompletedKafkaTemplateMock() {
        return Mockito.mock(KafkaTemplate.class);
    }
    @SuppressWarnings("unchecked")
    @Bean
    @Primary
    public KafkaTemplate<String, NotificationRequest> notificationKafkaTemplateMock() {
        return Mockito.mock(KafkaTemplate.class);
    }
}
