package com.qrlogi.domain.notification.consumer;

import com.qrlogi.domain.notification.dto.NotificationRequest;
import com.qrlogi.domain.notification.entity.NotificationType;
import com.qrlogi.domain.notification.event.ScanCompletedEvent;
import com.qrlogi.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;


@Component
@Slf4j
@RequiredArgsConstructor
public class ScannedCompletedListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "scan.completed", groupId = "notification-group")
    public void handle(ScanCompletedEvent scanCompletedEvent) {
        log.info("[Kafka] Event occurred: {}", scanCompletedEvent);


        String emailTitle = String.format("[QC Scan Completed] Order #%s", scanCompletedEvent.getOrderNumber());
        String messageContent = String.format("""
                       [Notification : QC Scan Completed]
                       
                       Hello %s
                       
                       The following products has successfully passed the qc:
                       
                       - Order No : %s
                       - Product : %s
                       - Order Qty : %d
                       - Scanned Qty : %d
                       - Scanned by : %s
                       - Shipment Status : %s
                        
                       thank you
                       
                       """,
                        scanCompletedEvent.getRepresentativeName(),
                        scanCompletedEvent.getOrderNumber(),
                        scanCompletedEvent.getProductName(),
                        scanCompletedEvent.getOrderedQty(),
                        scanCompletedEvent.getScannedQty(),
                        scanCompletedEvent.getShipmentStatus()
            );


        NotificationRequest request = NotificationRequest.builder()
                .type(NotificationType.EMAIL)
                .receiverName(scanCompletedEvent.getRepresentativeName())
                .receiverEmail(scanCompletedEvent.getRepresentativeEmail())
                .emailTitle(emailTitle)
                .message(messageContent)
                .build();


        notificationService.sendEmailNotification(request);
    }



}
