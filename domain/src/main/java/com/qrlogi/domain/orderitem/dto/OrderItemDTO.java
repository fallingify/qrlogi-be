package com.qrlogi.domain.orderitem.dto;

import com.qrlogi.domain.orderitem.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OrderItemDTO {

    private String productName;
    private int orderedQty;
    private int shippedQty;
    private String shipmentStatus;

    //entity -> dto
    public static OrderItemDTO toDTO(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getProduct().getName(),
                orderItem.getOrderedQty(),
                orderItem.getShippedQty(),
                orderItem.getShipmentStatus().name()

        );

    }
}
