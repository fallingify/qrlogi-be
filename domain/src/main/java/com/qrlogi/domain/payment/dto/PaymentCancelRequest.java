package com.qrlogi.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentCancelRequest {
    private String paymentKey;
    private int cancelAmount;
}
