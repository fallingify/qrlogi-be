package com.qrlogi.domain.payment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TossConfirmRequest {
    private String paymentKey;
    private String orderId;
    private int amount;

}
