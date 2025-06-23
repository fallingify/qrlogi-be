package com.qrlogi.domain.shipment.service;

import com.qrlogi.domain.orderitem.entity.OrderItem;
import com.qrlogi.domain.orderitem.repository.OrderItemRepository;
import com.qrlogi.domain.shipment.dto.ShippingRequest;
import com.qrlogi.domain.shipment.dto.ShippingResponse;
import com.qrlogi.domain.shipment.validator.ShipmentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingService {

    private final OrderItemRepository orderItemRepository;
    private final ShipmentValidator shipmentValidator;

    public ShippingResponse doShipping(ShippingRequest shippingRequest) {

        OrderItem item =  shipmentValidator.validateShippmentExists(shippingRequest.getOrderItemId());

        return ShippingResponse.builder()
                .orderItemId(item.getId())
                .productName(item.getProduct().getName())
                .orderQty(item.getOrderedQty())
                .shippedQty(item.getShippedQty())
                .containerSerial(shippingRequest.getContainerSerial())
                .inspector1(shippingRequest.getInspector1())
                .inspector2(shippingRequest.getInspector2())
                .qc(item.getShippedQty() >= item.getOrderedQty()) //qc
                .build();

    }
}
