package com.qrlogi.domain.order.repository;

import com.qrlogi.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, String> {
    Optional<Orders> findByOrderNumber(String orderNumber);
}
