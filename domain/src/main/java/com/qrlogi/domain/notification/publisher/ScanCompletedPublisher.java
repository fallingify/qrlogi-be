package com.qrlogi.domain.notification.publisher;

import com.qrlogi.domain.notification.dto.ScanCompletedEventDto;
import com.qrlogi.domain.notification.producer.ScanCompletedProducer;
import com.qrlogi.domain.orderitem.entity.ShipmentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 발행 책임
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ScanCompletedPublisher {

    private final ScanCompletedProducer producer;

    public void publishScanCompleted(ScanCompletedEventDto dto) {
        if (dto == null || dto.getShipmentStatus() != ShipmentStatus.SHIPPED) {
            return;
        }

        log.info("[Kafka Publish] Scan completed. orderNumber={}, scannedQty={}", dto.getOrderNumber(), dto.getScannedQty());
        producer.send(dto.toEvent());


    }

}
