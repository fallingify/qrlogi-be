package com.qrlogi.domain.shipment.service;

import com.qrlogi.domain.orderitem.entity.OrderItem;
import com.qrlogi.domain.orderitem.repository.OrderItemRepository;
import com.qrlogi.domain.shipment.dto.ShippingRequest;
import com.qrlogi.domain.shipment.dto.ShippingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingService {

    private final OrderItemRepository orderItemRepository;

    public int getShippedQuantity(Long orderItemId) {
        OrderItem item = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new IllegalArgumentException("주문 항목이 존재하지 않습니다."));

        return item.getShippedQty();
    }


    public ShippingResponse doShipping(ShippingRequest shippingRequest) {
        OrderItem item = orderItemRepository.findById(shippingRequest.getOrderItemId())
                .orElseThrow(() -> new IllegalArgumentException("주문 항목이 존재하지 않습니다."));

        item.addShippingQty(shippingRequest.getShippedQty());

        return ShippingResponse.builder()
                .orderItemId(item.getId())
                .productName(item.getProduct().getName())
                .orderQty(item.getShippedQty())
                .shippedQty(item.getShippedQty())
                .containerSerial(shippingRequest.getContainerSerial())
                .inspector1(shippingRequest.getInspector1())
                .inspector2(shippingRequest.getInspector2())
                .qc(item.getShippedQty() >= item.getOrderedQty()) //수량검수통과여부
                .build();

    }
}
