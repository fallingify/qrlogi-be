package com.qrlogi.domain.document.dto;

import com.qrlogi.domain.orderitem.entity.OrderItem;
import com.qrlogi.domain.orderitem.entity.OrderItemSerial;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoogleSheetDto {

    private String orderNumber;
    private String productName;
    private String modelCode;
    private String serial;
    private String status;
    private String scannedAt;


    public static GoogleSheetDto from(OrderItemSerial serial, boolean isScanned) {
        return GoogleSheetDto.builder()
                .orderNumber(serial.getOrderItem().getOrder().getOrderNumber())
                .productName(serial.getOrderItem().getProduct().getName())
                .modelCode(serial.getOrderItem().getProduct().getModelCode())
                .serial(serial.getSerial())
                .status(isScanned ? "scanned" : "unscanned")
                .scannedAt(isScanned ? LocalDateTime.now().toString() : "")
                .build();
    }

    @Override
    public String toString() {
        return "GoogleSheetDto{" +
                "orderNumber='" + orderNumber + '\'' +
                ", productName='" + productName + '\'' +
                ", modelCode='" + modelCode + '\'' +
                ", serial='" + serial + '\'' +
                ", status='" + status + '\'' +
                ", scannedAt='" + scannedAt + '\'' +
                '}';
    }


}
