package com.qrlogi.domain.payment.dto;

import com.qrlogi.domain.payment.entity.PaymentMethod;
import com.qrlogi.domain.payment.entity.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentHistoryDto {

    private String orderId;
    private String paymentKey;
    private int amount;
    private PaymentMethod method;
    private PaymentStatus status;
    private LocalDateTime paidAt;

}
