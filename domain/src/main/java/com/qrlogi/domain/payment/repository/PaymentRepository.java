package com.qrlogi.domain.payment.repository;

import com.qrlogi.domain.order.entity.Orders;
import com.qrlogi.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {


    Optional<Payment> findByOrder(Orders order);
    Optional<Payment> findByOrderId(String orderId);
    Optional<Payment> findByPaymentKey(String paymentKey);
}
