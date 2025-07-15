package com.qrlogi.domain.payment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentRequest {

    private int amount;
    private String orderId;
    private String paymentKey;

}
