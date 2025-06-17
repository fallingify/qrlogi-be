package com.qrlogi.domain.orderitem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemDTO {
    private String productName;
    private int qty;

}
