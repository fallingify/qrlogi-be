package com.qrlogi.domain.shippmentItem.service;

import com.qrlogi.domain.product.entity.Product;
import com.qrlogi.domain.product.repository.ProductRepository;
import com.qrlogi.domain.shipment.entity.Shipment;
import com.qrlogi.domain.shipment.repository.ShipmentRepository;
import com.qrlogi.domain.shippmentItem.dto.ShipmentItemRequest;
import com.qrlogi.domain.shippmentItem.dto.ShipmentItemResponse;
import com.qrlogi.domain.shippmentItem.entity.ShipmentItem;
import com.qrlogi.domain.shippmentItem.repository.ShipmentItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//컨테이너 별

@Service
@RequiredArgsConstructor
public class ShipmentItemService {


    private final ShipmentItemRepository shipmentItemRepository;
    private final ShipmentRepository shipmentRepository;
    private final ProductRepository productRepository;

    @Transactional
    public ShipmentItemResponse createShipmentItem(ShipmentItemRequest request) {
        Shipment shipment = shipmentRepository.findById(request.getShipmentId())
                .orElseThrow(() -> new IllegalArgumentException("Shipment not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        ShipmentItem item = ShipmentItem.builder()
                .shipment(shipment)
                .product(product)
                .qty(request.getQty())
                .build();


        ShipmentItem saved = shipmentItemRepository.save(item);

        return ShipmentItemResponse.builder()
                .id(saved.getId())
                .productName(product.getName())
                .qty(saved.getQty())
                .build();

    }
}
