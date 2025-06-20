package com.qrlogi.domain.orderitem.service;

import com.qrlogi.domain.order.entity.Orders;
import com.qrlogi.domain.order.repository.OrderRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemSerialRepository orderItemSerialRepository;
    private final SnowflakeIdGenerator idGenerator;
    private final QrService qrService;



    public List<OrderItemDTO> getOrderItems(String orderId) {

       Orders order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("없는 주문입니다."));

       List<OrderItem> orderItems = orderItemRepository.findAllByOrder(order);

        return orderItems.stream()
                .map(OrderItemDTO::toDTO)
                .collect(Collectors.toList());
    }


    @Transactional
    public List<OrderItemSerialResponse> generateSerials(String orderId) {

        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("없는 주문입니다."));

        List<OrderItem> orderItems = orderItemRepository.findAllByOrder(order);
        List<OrderItemSerial> allSerials = new ArrayList<>();

        for (OrderItem item : orderItems) {
            int quata = item.getOrderedQty();
            List<OrderItemSerial> serials = new ArrayList<>();

            for (int i = 0; i < quata; i++) {
                String serialId = String.valueOf(idGenerator.nextId());
                String qrUrl = qrService.createQrUrl(serialId); //qrImgURL을 생성


                OrderItemSerial serial = OrderItemSerial.builder()
                        .serial(serialId)
                        .isScanned(false)
                        .orderItem(item)
                        .createdAt(LocalDateTime.now())
                        .qrImgUrl(qrUrl)
                        .build();

                serials.add(serial);
            }

            orderItemSerialRepository.saveAll(serials);
            allSerials.addAll(serials);


        }

        return allSerials.stream()
                .map(OrderItemSerialResponse::toDTO)
                .collect(Collectors.toList());

    }//end




}//end