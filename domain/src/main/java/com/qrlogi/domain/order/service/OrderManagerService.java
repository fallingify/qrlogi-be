package com.qrlogi.domain.order.service;

import com.qrlogi.domain.order.dto.OrderManagerRequest;
import com.qrlogi.domain.order.dto.OrderManagerResponse;
import com.qrlogi.domain.order.entity.OrderManager;
import com.qrlogi.domain.order.entity.Orders;
import com.qrlogi.domain.order.repository.OrderManagerRepository;
import com.qrlogi.domain.order.repository.OrderRepository;
import com.qrlogi.domain.order.validator.OrderManagerValidator;
import com.qrlogi.domain.order.validator.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  담당자 등록 배정
 */
@Service
@RequiredArgsConstructor
public class OrderManagerService {

    private final OrderManagerRepository orderManagerRepository;
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final OrderManagerValidator orderManagerValidator;

    @Transactional
    public Long registerManager(OrderManagerRequest request) {

        OrderManager manager = OrderManager.builder()
                .managerName(request.getManagerName())
                .managerEmail(request.getManagerEmail())
                .build();

        return orderManagerRepository.save(manager).getId();

    }

    public OrderManagerResponse getManagerById(Long managerId) {

        OrderManager manager = orderManagerValidator.validateOrderManager(managerId);


        List<String> orderNumberList = manager.getOrderList().stream()
                .map(Orders::getOrderNumber)
                .toList();

        return OrderManagerResponse.builder()
                .id(manager.getId())
                .managerName(manager.getManagerName())
                .managerEmail(manager.getManagerEmail())
                .orderlist(orderNumberList)
                .build();
    }


    @Transactional
    public void assignOrder(Long managerId, String orderNumber) {

        OrderManager manager = orderManagerValidator.validateOrderManager(managerId);
        Orders order = orderValidator.validateByOrderNumber(orderNumber);

        if (order.getOrderManager() != null) {
            order.getOrderManager().getOrderList().remove(order); // 이전 매니저와의 관계 제거
        }

        manager.addOrder(order);

    }


}
