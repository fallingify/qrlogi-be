package com.qrlogi.domain.orderitem.orderitem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemDTO {
    private String productName;
    private int qty;

}
