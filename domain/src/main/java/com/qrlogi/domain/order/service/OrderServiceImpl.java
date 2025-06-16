package com.qrlogi.domain.order.service;

import com.qrlogi.domain.buyer.entity.Buyer;
import com.qrlogi.domain.buyer.repository.BuyerRepository;
import com.qrlogi.domain.order.dto.OrderRequest;
import com.qrlogi.domain.order.dto.OrderResponse;
import com.qrlogi.domain.order.entity.OrderStatus;
import com.qrlogi.domain.order.entity.Orders;
import com.qrlogi.domain.order.repository.OrderRepository;
import com.qrlogi.domain.orderitem.orderitem.dto.OrderItemDTO;
import com.qrlogi.domain.orderitem.orderitem.entity.OrderItem;
import com.qrlogi.domain.orderitem.orderitem.repository.OrderItemRepository;
import com.qrlogi.domain.product.entity.Product;
import com.qrlogi.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BuyerRepository buyerRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;


    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest request) {

        Buyer buyer = buyerRepository.findById(request.getBuyerId()).orElseThrow(() -> new IllegalArgumentException("Buyer not found"));

        //LocalDataTime + 랜덤숫자(3자리)
        Long orderNum = getOrderNum();

        Orders order = Orders.builder()
                .id(UUID.randomUUID().toString())
                .orderNumber(orderNum)
                .buyer(buyer)
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.REQUESTED)
                .build();
        orderRepository.save(order);

        List<OrderItem> savedItems = request.getOrderlist().stream()
                .map(req -> {
                    Product product = productRepository.findById(req.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

                    return OrderItem.builder()
                            .order(order)
                            .product(product)
                            .qty(req.getQty())
                            .build();
                })
                .map(orderItemRepository::save)
                .toList();


        return toResponse(order, savedItems);
    }




    @Transactional(readOnly = true)
    @Override
    public OrderResponse getOrderById(String orderId) {

        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        List<OrderItem> items = orderItemRepository.findAllByOrder(order);

        return toResponse(order, items);

    }

    @Override
    public List<OrderResponse> getAllOrders() {

        List<Orders> orders = orderRepository.findAll();

        return orders.stream()
                .map(i -> {
                    List<OrderItem> items = orderItemRepository.findAllByOrder(i);

                    return toResponse(i, items);
                })
                .collect(Collectors.toList());
    };


    @Transactional
    @Override
    public void cancelOrder(String orderId) {

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.cancel();
    }


    private static Long getOrderNum() {
        String timePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int randomPart = RandomUtils.nextInt(100, 1000);
        return Long.parseLong(timePart + randomPart);
    }


    private OrderResponse toResponse(Orders order, List<OrderItem> items) {

        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getBuyer().getName(),
                order.getOrderDate(),
                order.getOrderStatus().name(),
                items.stream()
                        .map(i -> new OrderItemDTO(i.getProduct().getName(), i.getQty()))
                        .collect(Collectors.toList())
        );
    }



}
