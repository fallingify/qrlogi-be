package com.qrlogi.domain.shipment.service;

import com.qrlogi.domain.inspection.dto.ScanRequest;
import com.qrlogi.domain.inspection.entity.ScanLog;
import com.qrlogi.domain.orderitem.entity.OrderItem;
import com.qrlogi.domain.orderitem.repository.OrderItemRepository;
import com.qrlogi.domain.inspection.repository.ScanLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final OrderItemRepository orderItemRepository;
    private final ScanLogRepository scanLogRepository;

    @Transactional
    public void doScan(ScanRequest scanRequest) {
        OrderItem item = orderItemRepository.findById(scanRequest.getOrderItemId())
                .orElseThrow(() -> new IllegalArgumentException("주문한적 없음") );

        item.addShippingQty(scanRequest.getQty());

        ScanLog log = ScanLog.builder()
                .orderItem(item)
                .scannedQty(scanRequest.getQty())
                .scannedAt(LocalDateTime.now())
                .scannedBy(scanRequest.getWorker())
                .build();


        scanLogRepository.save(log);
        orderItemRepository.save(item);


    }
}
