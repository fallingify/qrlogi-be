package com.qrlogi.domain.shipment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingRequest {

    private Long orderItemId;
    private int shippedQty;  // 이번에 출고 처리된 수
    private String containerSerial; // 컨테이너 일련번호
    private String inspector1;
    private String inspector2;
}
