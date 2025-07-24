package com.qrlogi.domain.order.service;

import com.qrlogi.domain.buyer.entity.Buyer;
import com.qrlogi.domain.order.dto.OrderRequest;
import com.qrlogi.domain.order.dto.OrderResponse;
import com.qrlogi.domain.order.entity.OrderStatus;
import com.qrlogi.domain.order.entity.Orders;
import com.qrlogi.domain.order.repository.OrderManagerRepository;
import com.qrlogi.domain.order.repository.OrderRepository;
import com.qrlogi.domain.order.validator.OrderValidator;
import com.qrlogi.domain.orderitem.dto.OrderItemDTO;
import com.qrlogi.domain.orderitem.entity.OrderItem;
import com.qrlogi.domain.orderitem.entity.ShipmentStatus;
import com.qrlogi.domain.orderitem.repository.OrderItemRepository;
import com.qrlogi.domain.product.entity.Product;
import com.qrlogi.domain.product.validator.ProductValidator;
import com.qrlogi.domain.user.entity.User;
import com.qrlogi.domain.user.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 주문 생성 조회
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderValidator orderValidator;
    private final UserValidator userValidator;
    private final ProductValidator productValidator;


    @Transactional
    public OrderResponse createOrder(OrderRequest request) {

        Buyer buyer = orderValidator.validateBuyerExists(request.getBuyerId());
        User user = userValidator.getCurrentUser();
        String orderNum = OrderNumberUtil.getOrderNumber();

        Orders order = Orders.builder()
                .id(UUID.randomUUID().toString())
                .orderNumber(orderNum)
                .user(user)
                .buyer(buyer)
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.REQUESTED)
                .build();

        orderRepository.save(order);

        List<OrderItem> savedItems = request.getOrderlist().stream()
                .map(req -> {
                    Product product = productValidator.validateProductExists(req.getProductId());

                    return OrderItem.builder()
                            .order(order)
                            .product(product)
                            .orderedQty(req.getQty())
                            .shippedQty(0)
                            .shipmentStatus(ShipmentStatus.PENDING)
                            .build();
                })
                .map(orderItemRepository::save)
                .toList();


        return toResponse(order, savedItems);
    }




    @Transactional(readOnly = true)
    public OrderResponse getOrderById(String orderId) {

        Orders order = orderValidator.validateOrderExists(orderId);
        List<OrderItem> items = orderItemRepository.findAllByOrder(order);

        return toResponse(order, items);

    }

    @Transactional
    public List<OrderResponse> getAllOrders() {

        List<Orders> orders = orderRepository.findAll();

        return orders.stream()
                .map(i -> {
                    List<OrderItem> items = orderItemRepository.findAllByOrder(i);

                    return toResponse(i, items);
                })
                .collect(Collectors.toList());
    }


    @Transactional
    public void cancelOrder(String orderId) {

        Orders order = orderValidator.validateOrderExists(orderId);
        order.cancel();
    }


    private OrderResponse toResponse(Orders order, List<OrderItem> items) {

        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getBuyer().getName(),
                order.getOrderDate(),
                order.getOrderStatus().name(),
                items.stream()
                        .map(i -> new OrderItemDTO(
                                i.getProduct().getName(),
                                i.getQty(),
                                i.getShippedQty(),
                                i.getShipmentStatus().name()))
                        .collect(Collectors.toList())
        );
    }



}
