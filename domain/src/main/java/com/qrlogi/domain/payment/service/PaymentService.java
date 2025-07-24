package com.qrlogi.domain.payment.service;

import com.qrlogi.domain.order.entity.Orders;
import com.qrlogi.domain.order.validator.OrderValidator;
import com.qrlogi.domain.payment.Validator.PaymentValidator;
import com.qrlogi.domain.payment.dto.*;
import com.qrlogi.domain.payment.entity.Payment;
import com.qrlogi.domain.payment.entity.PaymentMethod;
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

    /**
     * c. 결제 승인 API(request는 클라이언트에서 받아옴)
     */
    public PaymentResponse approvePayment(PaymentRequest request) {

        Orders order = orderValidator.validateOrderExists(request.getOrderId());
        String tossPayUrl = "https://api.tosspayments.com/v1/payments/" + request.getPaymentKey();

        HttpHeaders header = createHttpHeaders();
        Map<String, Object> body = Map.of(
                "orderId", request.getOrderId(),
                "amount", request.getAmount());

        try {
            //TODO: {} 안 코드 래핑해서 따로 분리하기
            HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, header);
            ResponseEntity<PaymentResponse> response = tossRestTemplate.exchange(
                            tossPayUrl,
                            HttpMethod.POST,
                            httpEntity,
                            PaymentResponse.class);

            PaymentResponse responseBody = response.getBody();
            if (responseBody == null) {
                throw new IllegalArgumentException("Payment Response is null");
            }

            paymentValidator.validatePaymentResponse(responseBody, request);
            Payment payment = PaymentFactory.toPayment(responseBody, order);
            paymentRepository.save(payment);

            return responseBody;

        } catch (RestClientException e) {
            //TODO : 아래 코드는 호출부에서 해야됨(이동필요) trasaction잡기, save()는 밖으로 빼기
            //TODO: {} 안 코드 래핑해서 따로 분리하기
            Orders failedPayment = orderValidator.validateOrderExists(request.getOrderId());

            Payment payment = Payment.builder()
                    .order(failedPayment)
                    .paymentKey(request.getPaymentKey())
                    .amount(request.getAmount())
                    .method(PaymentMethod.CARD) //TODO : 수정필요, method변경해야함
                    .status(PaymentStatus.FAILED)
                    .build();

            payment.markFailed();
            //TODO : 아래 코드는 호출부에서 해야됨(이동필요) trasaction잡기, save()는 밖으로 빼기
            paymentRepository.save(payment);

            log.error("토스 API 연결 에러 문제: {}", e.getMessage());
            throw new IllegalArgumentException("Payment failed");
        }


    }//end


    public PaymentHistoryDto getPaymentHistory(String orderId) {
        Payment payment = paymentValidator.validateProductExists(orderId);
        return PaymentFactory.toPaymentHistoryDto(payment);
    }


    /**
     * return 타입 dto로 다시 구성하기
     *
     * @return
     */
    public PaymentResponse cancelPayment(PaymentCancelRequest request) {

        String url = "https://api.tosspayments.com/v1/payments/" + request.getPaymentKey() + "/cancel";

        HttpHeaders header = createHttpHeaders();
        Map<String, Object> body = Map.of(
                "cancelReason", "사용자 요청",
                "cancelAmount", request.getCancelAmount()
        );

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, header);

        try {
            ResponseEntity<PaymentResponse> response = tossRestTemplate.exchange(
                    url, HttpMethod.POST, httpEntity, PaymentResponse.class);
            PaymentResponse responseBody = response.getBody();
            //TODO : 아래 코드는 없어도 됨
            if (responseBody == null) {
                throw new IllegalArgumentException("Response body is null");
            }

            //엔티티조횐
            Payment payment = paymentValidator.validatePaymentExistByPaymentKey(request.getPaymentKey());

            // 상태가 반영된 새로운 Payment 객체 생성 및 저장
            Payment canceledPayment = PaymentFactory.toPayment(responseBody, payment.getOrder());
            payment.markCanceled();
           //TODO : 아래 코드는 호출부에서 해야됨(이동필요) trasaction잡기, save()는 밖으로 빼기
            paymentRepository.save(canceledPayment);

            return responseBody;

        } catch (RestClientException e) {
            log.error("토스 API 연결 에러 문제: {}", e.getMessage());
            throw new RuntimeException("Payment cancel failed");
        }
    }

    public List<PaymentHistoryDto> getAllPaymentHistories(){

        return paymentRepository.findAll().stream()
                .map(PaymentFactory::toPaymentHistoryDto)
                .collect(Collectors.toList());
    }


    public void validatePayOrNot(Orders order){
        if(order.isRequirePayment() && !isPaymentProcessed(order)) {
            throw new IllegalArgumentException("Payment is not required. Please pay");
        }

    }



    /**
     * 헤더 생성
     */
    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(tossSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }
}
