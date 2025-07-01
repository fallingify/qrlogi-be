package com.qrlogi.domain.order.validator;

import com.qrlogi.domain.buyer.entity.Buyer;
import com.qrlogi.domain.buyer.repository.BuyerRepository;
import com.qrlogi.domain.order.entity.Orders;
import com.qrlogi.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderValidator {

    private final BuyerRepository buyerRepository;
    private final OrderRepository orderRepository;


    public Buyer validateBuyerExists(Long buyerId) {

        return buyerRepository.findById(buyerId)
                .orElseThrow(() -> new IllegalArgumentException("Buyer not found"));
    }

    public Orders validateOrderExists(String orderId) {

        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }


    public Orders validateByOrderNumber(String orderNumber) {
        try{
            return orderRepository.findByOrderNumber(orderNumber)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid Order Number");
        }
    }

    //삭제
    // OrderValidator
    public Orders validateByOrderNumberWithManager(String orderNumber) {
        return orderRepository.findByOrderNumberWithManager(orderNumber)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }


}
