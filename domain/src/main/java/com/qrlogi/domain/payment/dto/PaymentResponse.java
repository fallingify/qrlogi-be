package com.qrlogi.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private String paymentKey;
    private String orderId;
    private int amountPaid;
    private String status;
    private String method;;
    private String approvedAt;



}
