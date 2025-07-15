package com.qrlogi.api.controller;

import com.qrlogi.domain.payment.dto.PaymentCancelRequest;
import com.qrlogi.domain.payment.dto.PaymentHistoryDto;
import com.qrlogi.domain.payment.dto.PaymentRequest;
import com.qrlogi.domain.payment.dto.PaymentResponse;
import com.qrlogi.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 결제 승인
     */
    @PostMapping("/confirm")
    public ResponseEntity<PaymentResponse> approve(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse response = paymentService.approvePayment(paymentRequest);
        return ResponseEntity.ok(response);
    }


    /**
     * 결제 여부 확인
     */
    @GetMapping("/view/{orderId}")
    public ResponseEntity<PaymentHistoryDto> view(@PathVariable String orderId) {
        return ResponseEntity.ok(paymentService.getPaymentHistory(orderId));
    }

    /**
     * 결제 취소
     */
    @PostMapping("/cancel")
    public ResponseEntity<PaymentResponse> cancel(@RequestBody PaymentCancelRequest paymentCancelRequest) {
        PaymentResponse response = paymentService.cancelPayment(paymentCancelRequest);
        return ResponseEntity.ok(response);
    }


    /**
     *  관리자용 결제전체목록확인
     */
    @GetMapping("/admin")
    public ResponseEntity<List<PaymentHistoryDto>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPaymentHistories());
    }

}
