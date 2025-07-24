package com.qrlogi.domain.payment.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class PaymentReadyResponse {

    private String customerKey; //UserId
    private String orderId;
    private String orderName;
    private int amount;
    private String customerName;
    private String customerEmail;


}
