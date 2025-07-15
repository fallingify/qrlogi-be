package com.qrlogi.domain.payment.factory;

import com.qrlogi.domain.order.entity.Orders;
import com.qrlogi.domain.payment.dto.PaymentHistoryDto;
import com.qrlogi.domain.payment.dto.PaymentResponse;
import com.qrlogi.domain.payment.entity.Payment;
import com.qrlogi.domain.payment.entity.PaymentMethod;
import com.qrlogi.domain.payment.entity.PaymentStatus;
import java.time.LocalDateTime;

public class PaymentFactory {

    //PaymentResponse -> Payment
    public static Payment toPayment(PaymentResponse response, Orders order) {

        Payment pay = Payment.builder()
                .order(order)
                .paymentKey(response.getPaymentKey())
                .amount(response.getAmountPaid())
                .method(PaymentMethod.valueOf(response.getMethod().toUpperCase()))
                .status(PaymentStatus.PAID)
                .paidAt(LocalDateTime.parse(response.getApprovedAt()))
                .build();
        pay.markPaid(response.getPaymentKey(), response.getAmountPaid());
        return pay;

    }

    //Payment -> PaymentHistoryDto
    public static PaymentHistoryDto toPaymentHistoryDto(Payment payment) {
        return PaymentHistoryDto.builder()
                .orderId(payment.getOrder().getId())
                .paymentKey(payment.getPaymentKey())
                .amount(payment.getAmount())
                .method(payment.getMethod())
                .status(payment.getStatus())
                .build();
    }



}
