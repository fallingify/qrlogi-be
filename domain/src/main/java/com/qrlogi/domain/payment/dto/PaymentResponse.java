package com.qrlogi.domain.payment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentResponse {

    private String paymentKey;
    private String orderId;
    private int amountPaid;
    private String status;
    private String method;;
    private String approvedAt;


}
