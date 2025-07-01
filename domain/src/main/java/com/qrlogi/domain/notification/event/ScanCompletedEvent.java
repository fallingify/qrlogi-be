package com.qrlogi.domain.notification.event;

import com.qrlogi.domain.orderitem.entity.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScanCompletedEvent {

    private String orderNumber;
    private String productName;
    private int orderedQty;
    private int scannedQty;
    private ShipmentStatus shipmentStatus;


}
