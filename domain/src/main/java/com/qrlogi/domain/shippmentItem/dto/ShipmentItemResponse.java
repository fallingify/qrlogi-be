package com.qrlogi.domain.shippmentItem.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShipmentItemResponse {

    private Long id;
    private String productName;
    private int qty;
}
