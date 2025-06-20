package com.qrlogi.domain.orderitem.dto;

import com.qrlogi.domain.orderitem.entity.OrderItem;
import com.qrlogi.domain.orderitem.entity.OrderItemSerial;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderItemSerialResponse {

    private Long id;
    private Boolean isScanned;
    private LocalDateTime createdAt;
    private String serial;
    private String qrImgUrl;

    public static OrderItemSerialResponse toDTO(OrderItemSerial orderItemSerial) {
        return new OrderItemSerialResponse(
                orderItemSerial.getId(),
                orderItemSerial.getIsScanned(),
                orderItemSerial.getCreatedAt(),
                orderItemSerial.getSerial(),
                orderItemSerial.getQrImgUrl()
        );
    }



}
