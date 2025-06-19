package com.qrlogi.domain.shippmentItem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentItemRequest {

    private Long shipmentId;
    private Long productId;
    private int qty;

}
