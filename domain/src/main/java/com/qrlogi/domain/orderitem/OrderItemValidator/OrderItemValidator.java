package com.qrlogi.domain.orderitem.OrderItemValidator;

import com.qrlogi.domain.orderitem.entity.OrderItem;
import com.qrlogi.domain.orderitem.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemValidator {

    private final OrderItemRepository orderItemRepository;

    public OrderItem validateByOrderItemIdExists(Long orderItemId) {

        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new IllegalArgumentException("OrderItem not found"));


    }
}
