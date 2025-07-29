package com.qrlogi.api.controller;

import com.qrlogi.domain.payment.dto.TossConfirmRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/widget")
@RequiredArgsConstructor
public class WidgetController {

    @Value("${toss.secret-key}")
    private String tossSecretKey;

    private final RestTemplate restTemplate;

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmPayment(@RequestBody TossConfirmRequest req) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(tossSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "orderId", req.getOrderId(),
                "amount", req.getAmount(),
                "paymentKey", req.getPaymentKey()
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Object> response = restTemplate.exchange(
                    "https://api.tosspayments.com/v1/payments/confirm",
                    HttpMethod.POST,
                    entity,
                    Object.class
            );

            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        }
    }
}
