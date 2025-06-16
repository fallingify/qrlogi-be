package com.qrlogi.domain.order.service;

import com.qrlogi.domain.order.dto.OrderRequest;
import com.qrlogi.domain.order.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
    OrderResponse getOrderById(String orderId);
    List<OrderResponse> getAllOrders();
    void cancelOrder(String orderId);

}