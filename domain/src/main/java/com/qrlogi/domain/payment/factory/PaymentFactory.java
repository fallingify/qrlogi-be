package com.qrlogi.domain.payment.factory;

import com.qrlogi.domain.order.entity.Orders;
import com.qrlogi.domain.payment.dto.PaymentHistoryDto;
import com.qrlogi.domain.payment.dto.PaymentReadyResponse;
import com.qrlogi.domain.payment.dto.PaymentRequest;
import com.qrlogi.domain.payment.dto.PaymentResponse;
import com.qrlogi.domain.payment.entity.Payment;
import com.qrlogi.domain.payment.entity.PaymentMethod;
import com.qrlogi.domain.payment.entity.PaymentStatus;
import com.qrlogi.domain.user.entity.User;

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


    // -> PaymentReady
    public static PaymentReadyResponse toPaymentReadyResponse(User user, PaymentRequest request) {
        return PaymentReadyResponse.builder()
                .customerKey(user.getId().toString())
                .orderId(request.getOrderId())
                .orderName("Qrlogistics 유료 패키지 결제")
                .amount(request.getAmount())
                .customerEmail(user.getEmail())
                .customerName(user.getUsername())
                .build();



    }


}
