package com.qrlogi.domain.orderitem.service;

import com.qrlogi.domain.order.entity.Orders;
import com.qrlogi.domain.order.repository.OrderRepository;
import com.qrlogi.domain.orderitem.dto.OrderItemDTO;
import com.qrlogi.domain.orderitem.entity.OrderItem;
import com.qrlogi.domain.orderitem.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;



    public List<OrderItemDTO> getOrderItems(String orderId) {
       Orders order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("없는 주문입니다."));

       List<OrderItem> orderItems = orderItemRepository.findAllByOrder(order);

        return orderItems.stream()
                .map(OrderItemDTO::toDTO)
                .collect(Collectors.toList());
    }



}