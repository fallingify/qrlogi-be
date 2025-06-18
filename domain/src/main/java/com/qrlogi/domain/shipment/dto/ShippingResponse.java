package com.qrlogi.domain.shipment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ShippingResponse {

    private Long orderItemId;
    private String productName;
    private int orderQty;
    private int shippedQty;
    private String containerSerial;
    private String inspector1;
    private String inspector2;
    private boolean qc; //Quality control, 계약동일 수량인지아닌지 검수 통과여부

}