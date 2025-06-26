package com.qrlogi.domain.notification.dto;

import com.qrlogi.domain.notification.event.ScanCompletedEvent;
import com.qrlogi.domain.orderitem.entity.OrderItem;
import com.qrlogi.domain.orderitem.entity.ShipmentStatus;
import lombok.Builder;
import lombok.Getter;

/**
 * 	이벤트 데이터를 깔끔하게 매핑하는 용도
 */
@Getter
@Builder
public class ScanCompletedEventDto {

    private String orderNumber;
    private String productName;
    private int orderedQty;
    private int scannedQty;
    private ShipmentStatus shipmentStatus;

    public ScanCompletedEvent toEvent(){
        return ScanCompletedEvent.builder()
                .orderNumber(orderNumber)
                .productName(productName)
                .orderedQty(orderedQty)
                .scannedQty(scannedQty)
                .shipmentStatus(shipmentStatus).build();

    }

    public static ScanCompletedEventDto toDTO(OrderItem orderItem, int scannedQty) {
        return ScanCompletedEventDto.builder()
                .orderNumber(orderItem.getOrder().getOrderNumber())
                .productName(orderItem.getProductName())
                .orderedQty(orderItem.getOrderedQty())
                .scannedQty(scannedQty)
                .shipmentStatus(orderItem.getShipmentStatus())
                .build();
    }
}
