package com.qrlogi.domain.order.validator;

import com.qrlogi.domain.order.entity.OrderManager;
import com.qrlogi.domain.order.repository.OrderManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderManagerValidator {

    private final OrderManagerRepository orderManagerRepository;

    public OrderManager validateOrderManager(Long orderManagerId) {
        return orderManagerRepository.findById(orderManagerId)
                .orElseThrow(() -> new IllegalArgumentException("OrderManager not found"));

    }

}
