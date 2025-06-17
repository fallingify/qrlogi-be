package com.qrlogi.domain.orderitem.dto;

import lombok.Data;

@Data
public class OrderItemRequest {

    private Long productId;
    private int qty;
}
