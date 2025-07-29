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

    // 결제 승인용
    public static Payment toApprovedPayment(PaymentResponse response, Orders order) {
        return Payment.builder()
                .order(order)
                .paymentKey(response.getPaymentKey())
                .amount(response.getAmountPaid())
                .method(PaymentMethod.valueOf(response.getMethod().toUpperCase()))
                .status(PaymentStatus.PAID)
                .paidAt(LocalDateTime.parse(response.getApprovedAt()))
                .build();
    }

    // 결제 취소용
    public static Payment toCanceledPayment(PaymentResponse response, Orders order) {
        return Payment.builder()
                .order(order)
                .paymentKey(response.getPaymentKey())
                .amount(response.getAmountPaid())
                .method(PaymentMethod.valueOf(response.getMethod().toUpperCase()))
                .status(PaymentStatus.CANCELED)
                .paidAt(LocalDateTime.parse(response.getApprovedAt()))
                .build();
    }

    //결제 목록 확인 용
    public static PaymentHistoryDto toPaymentHistoryDto(Payment payment) {
        return PaymentHistoryDto.builder()
                .orderId(payment.getOrder().getId())
                .paymentKey(payment.getPaymentKey())
                .amount(payment.getAmount())
                .method(payment.getMethod())
                .status(payment.getStatus())
                .build();
    }


    // 결제 승인 요청용
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

    //결제 승인실패용
    public static Payment toFailedPayment(PaymentRequest request, Orders order) {
        return Payment.builder()
                .order(order)
                .paymentKey(request.getPaymentKey())
                .amount(request.getAmount())
                .method(PaymentMethod.CARD)
                .status(PaymentStatus.FAILED)
                .build();

    }

    //결제완료용
    public static PaymentResponse toPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .paymentKey(payment.getPaymentKey())
                .orderId(payment.getOrder().getId())
                .amountPaid(payment.getAmount())
                .status(payment.getStatus().name())
                .method(payment.getMethod().name().toUpperCase())
                .approvedAt(payment.getPaidAt() != null ? payment.getPaidAt().toString() : null)
                .build();
    }

}
