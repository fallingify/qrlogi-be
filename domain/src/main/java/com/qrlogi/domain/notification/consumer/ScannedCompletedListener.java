package com.qrlogi.domain.notification.consumer;

import com.qrlogi.domain.notification.dto.NotificationRequest;
import com.qrlogi.domain.notification.entity.NotificationType;
import com.qrlogi.domain.notification.event.ScanCompletedEvent;
import com.qrlogi.domain.notification.service.NotificationService;
import com.qrlogi.domain.order.entity.OrderManager;
import com.qrlogi.domain.order.entity.Orders;
import com.qrlogi.domain.order.validator.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScannedCompletedListener {

    private final NotificationService notificationService;
    private final OrderValidator orderValidator;

    @KafkaListener(topics = "scan.completed", groupId = "scan-notification-group")
    public void handle(ScanCompletedEvent scanCompletedEvent) {

        log.info("[Kafka] Event occurred: {}", scanCompletedEvent);

//        Orders order = orderValidator.validateByOrderNumber(scanCompletedEvent.getOrderNumber());
        Orders order = orderValidator.validateByOrderNumberWithManager(scanCompletedEvent.getOrderNumber());//삭제(임시)


        OrderManager manager = order.getOrderManager();
        if (manager == null) {
            throw new IllegalStateException("Manager not found, Order Number : " + order.getOrderNumber());
        }

        //EMAIL CONTENT, TITLE
        String emailTitle = String.format("[QC Scan Completed] Order #%s", scanCompletedEvent.getOrderNumber());
        String messageContent = String.format("""
                       [Notification : QC Scan Completed]
                       
                       Hello %s
                       
                       The following products has successfully passed the qc:
                       
                       - Order No : %s
                       - Product : %s
                       - Order Qty : %d
                       - Scanned Qty : %d
                       - Shipment Status : %s
                        
                       thank you
                       
                       """,
                        manager.getManagerName(),
                        scanCompletedEvent.getOrderNumber(),
                        scanCompletedEvent.getProductName(),
                        scanCompletedEvent.getOrderedQty(),
                        scanCompletedEvent.getScannedQty(),
                        scanCompletedEvent.getShipmentStatus()
            );


        NotificationRequest request = NotificationRequest.builder()
                .type(NotificationType.EMAIL)
                .receiverName(manager.getManagerName())
                .receiverEmail(manager.getManagerEmail())
                .emailTitle(emailTitle)
                .message(messageContent)
                .build();


        notificationService.sendEmailNotification(request);
    }



}
