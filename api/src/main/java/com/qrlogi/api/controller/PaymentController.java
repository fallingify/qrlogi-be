package com.qrlogi.api.controller;

import com.qrlogi.domain.order.entity.Orders;
import com.qrlogi.domain.payment.dto.*;
import com.qrlogi.domain.payment.factory.PaymentFactory;
import com.qrlogi.domain.payment.service.PaymentService;
import com.qrlogi.domain.user.entity.User;
import com.qrlogi.domain.user.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final UserValidator userValidator;

    /**
     * 결제 정보 전달 (-> 백엔드)
     */
    @PostMapping("/request")
    public ResponseEntity<PaymentReadyResponse> preparePayment(@RequestBody PaymentRequest request) {

        User currentUser = userValidator.getCurrentUser();
        PaymentReadyResponse response = PaymentFactory.toPaymentReadyResponse(currentUser, request);
        return  ResponseEntity.ok(response);

    }

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
        PaymentHistoryDto response = paymentService.getPaymentHistory(orderId);
        return ResponseEntity.ok(response);
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

        List<PaymentHistoryDto> response = paymentService.getAllPaymentHistories();
        return ResponseEntity.ok(response);
    }

}
