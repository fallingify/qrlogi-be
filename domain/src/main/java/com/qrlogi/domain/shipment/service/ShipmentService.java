package com.qrlogi.domain.shipment.service;

import com.qrlogi.domain.ShipmentLog.dto.ScanRequest;
import com.qrlogi.domain.orderitem.orderitem.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final OrderItemRepository orderItemRepository;
    prv


    @Transactional
    public void goScan(ScanRequest scanRequest) {
        orderItemRepository.findById(scanRequest.getOrderItemId()).orElseThrow(
                () -> new IllegalArgumentException("주문한적 없음")
        );
    }
}
