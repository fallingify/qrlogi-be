package com.qrlogi.domain.shipment.validator;

import com.qrlogi.domain.orderitem.entity.OrderItem;
import com.qrlogi.domain.orderitem.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShipmentValidator {

    private final OrderItemRepository orderItemRepository;

    public OrderItem validateShippmentExists(Long orderItemId) {

       return  orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new IllegalArgumentException("OrderItem not found"));

    }
}
