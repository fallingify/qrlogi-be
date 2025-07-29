package com.qrlogi.domain.payment.service;

import com.qrlogi.domain.order.entity.Orders;
import com.qrlogi.domain.order.validator.OrderValidator;
import com.qrlogi.domain.payment.Validator.PaymentValidator;
import com.qrlogi.domain.payment.dto.*;
import com.qrlogi.domain.payment.entity.Payment;
import com.qrlogi.domain.payment.entity.PaymentStatus;
import com.qrlogi.domain.payment.factory.PaymentFactory;
import com.qrlogi.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderValidator orderValidator;
    private final PaymentValidator paymentValidator;
    private final RestTemplate tossRestTemplate;


    @Value("${toss.secret-key}")
    private String tossSecretKey;

    public boolean isPaymentProcessed(Orders order) {

        return paymentRepository.findByOrder(order)
                .map(payment -> payment.getStatus() == PaymentStatus.PAID)
                .orElse(false);
    }

    //결제승인
    public Payment approvePayment(PaymentRequest request) {

        String paymentKey = request.getPaymentKey();
        try {
            Orders order = orderValidator.validateOrderExists(request.getOrderId());
            Map<String, Object> body = Map.of(
                    "orderId", request.getOrderId(),
                    "amount", request.getAmount()
            );
            PaymentResponse responseBody = requestApproval(paymentKey, body);
            paymentValidator.validatePaymentResponse(responseBody, request);

            return PaymentFactory.toApprovedPayment(responseBody, order);

        } catch (RestClientException e) {
            Orders failedOrder= orderValidator.validateOrderExists(request.getOrderId());
            return  PaymentFactory.toFailedPayment(request,failedOrder);
        }
    }

    //Http 호출부 생성
    private PaymentResponse requestApproval(String paymentKey, Map<String, Object> body) {

        String tossPayUrl = "https://api.tosspayments.com/v1/payments/" + paymentKey;
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, createHttpHeaders());
        ResponseEntity<PaymentResponse> response = tossRestTemplate.exchange(tossPayUrl, HttpMethod.POST, httpEntity, PaymentResponse.class);
        return response.getBody();
    }


    public Payment cancelPayment(PaymentCancelRequest request) {

        try {
            Map<String, Object> body = Map.of(
                    "cancelReason", "사용자 요청",
                    "cancelAmount", request.getCancelAmount()
            );

            PaymentResponse responseBody = requestCancel(request.getPaymentKey(), body);
            Payment payment = paymentValidator.validatePaymentExistByPaymentKey(request.getPaymentKey());

            Payment canceled = PaymentFactory.toCanceledPayment(responseBody, payment.getOrder());
            canceled.markCanceled();
            return canceled;

        } catch (RestClientException e) {
            throw new RuntimeException("Payment cancel failed");
        }
    }

    private PaymentResponse requestCancel(String paymentKey, Map<String, Object> body) {
        String url = "https://api.tosspayments.com/v1/payments/" + paymentKey+ "/cancel";
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, createHttpHeaders());
        ResponseEntity<PaymentResponse> response = tossRestTemplate.exchange(url, HttpMethod.POST, httpEntity, PaymentResponse.class);
        return response.getBody();
    }


    //헤더생성
    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(tossSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }


    //기록확인용
    public List<PaymentHistoryDto> getAllPaymentHistories(){
        return paymentRepository.findAll().stream()
                .map(PaymentFactory::toPaymentHistoryDto)
                .collect(Collectors.toList());
    }


    public PaymentHistoryDto getPaymentHistory(String orderId) {
        Payment payment = paymentValidator.validateProductExists(orderId);
        return PaymentFactory.toPaymentHistoryDto(payment);
    }


    //결제여부판단
    public void validatePayOrNot(Orders order){
        if(order.isRequirePayment() && !isPaymentProcessed(order)) {
            throw new IllegalArgumentException("Payment is not required. Please pay");
        }

    }



}
