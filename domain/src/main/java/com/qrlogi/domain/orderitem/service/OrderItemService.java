package com.qrlogi.domain.orderitem.service;

import com.qrlogi.domain.order.entity.Orders;
import com.qrlogi.domain.order.validator.OrderValidator;
import com.qrlogi.domain.orderitem.dto.OrderItemDTO;
import com.qrlogi.domain.orderitem.dto.OrderItemSerialResponse;
import com.qrlogi.domain.orderitem.entity.OrderItem;
import com.qrlogi.domain.orderitem.entity.OrderItemSerial;
import com.qrlogi.domain.orderitem.repository.OrderItemRepository;
import com.qrlogi.domain.orderitem.repository.OrderItemSerialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemSerialRepository orderItemSerialRepository;
    private final SnowflakeIdGenerator idGenerator;
    private final QrService qrService;
    private final OrderValidator orderValidator;

    public List<OrderItemDTO> getOrderItems(String orderId) {

       Orders order = orderValidator.validateOrderExists(orderId);
       List<OrderItem> orderItems = orderItemRepository.findAllByOrder(order);

        return orderItems.stream()
                .map(OrderItemDTO::toDTO)
                .collect(Collectors.toList());
    }

    //Serial생성
    @Transactional
    public List<OrderItemSerialResponse> generateSerials(String orderId) {

        Orders order = orderValidator.validateOrderExists(orderId);
        List<OrderItem> allOrderItems = orderItemRepository.findAllByOrder(order);

        List<OrderItemSerial> serialsToSave = allOrderItems.stream()
                .map(this::createSerialsForOrderItem)
                .flatMap(List::stream)
                .toList();

        orderItemSerialRepository.saveAll(serialsToSave);

        return serialsToSave.stream()
                .map(OrderItemSerialResponse::toDTO)
                .toList();

    }


    //Serial생성을 위한 OrderItem 루프, createOrderItemSerial호출
    private List<OrderItemSerial> createSerialsForOrderItem(OrderItem orderItem) {

        int orderedQty = orderItem.getOrderedQty();

        return IntStream.range(0, orderedQty)
                .mapToObj(i -> createOrderItemSerial(orderItem)).toList();

    }

    //일련변호 생성된거 주입
    private OrderItemSerial createOrderItemSerial(OrderItem orderItem) {
        String serial = String.valueOf(idGenerator.nextId());
        String qrUrl = qrService.createQrUrl(serial);

        return OrderItemSerial.builder()
                .serial(serial)
                .isScanned(false)
                .orderItem(orderItem)
                .createdAt(LocalDateTime.now())
                .qrImgUrl(qrUrl)
                .build();

    }



}