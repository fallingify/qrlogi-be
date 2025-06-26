package com.qrlogi.domain.notification.publisher;

import com.qrlogi.domain.notification.dto.ScanCompletedEventDto;
import com.qrlogi.domain.notification.producer.ScanCompletedProducer;
import com.qrlogi.domain.orderitem.entity.ShipmentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 발행 책임
 */
@Component
@RequiredArgsConstructor
public class ScanCompletedPublisher {

    private final ScanCompletedProducer producer;

    public void publishScanCompleted(ScanCompletedEventDto dto) {
        if(ShipmentStatus.SHIPPED.equals(dto.getShipmentStatus())) {
            producer.send(dto.toEvent());
        }
    }

}
