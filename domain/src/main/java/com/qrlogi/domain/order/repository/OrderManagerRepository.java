package com.qrlogi.domain.order.repository;

import com.qrlogi.domain.order.entity.OrderManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderManagerRepository extends JpaRepository<OrderManager, Long> {

    Optional<OrderManager> findByManagerEmail(String email);
}
