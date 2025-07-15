package com.qrlogi.domain.payment.PaymentValidator;

import com.qrlogi.domain.payment.dto.PaymentRequest;
import com.qrlogi.domain.payment.dto.PaymentResponse;
import com.qrlogi.domain.payment.entity.Payment;
import com.qrlogi.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentValidator {

    private final PaymentRepository paymentRepository;

    public Payment validateProductExists(String orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("payment not found for this order"));
    }


    public void validatePaymentResponse(PaymentResponse response , PaymentRequest request) {
        if(response.getAmountPaid() != request.getAmount()) {
            throw new IllegalArgumentException("Payment amount mismatch (paid amount is not same as ordered) ");
        }

    }

    public Payment validatePaymentExistByPaymentKey(String paymentKey) {

       return paymentRepository.findByPaymentKey(paymentKey)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

    }
}
