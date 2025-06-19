package com.qrlogi.domain.inspection.service;

import com.qrlogi.domain.inspection.dto.ScanRequest;
import com.qrlogi.domain.inspection.dto.ScanResponse;
import com.qrlogi.domain.inspection.entity.ScanLog;
import com.qrlogi.domain.inspection.entity.ScanStatus;
import com.qrlogi.domain.inspection.repository.ScanLogRepository;
import com.qrlogi.domain.orderitem.entity.OrderItem;
import com.qrlogi.domain.orderitem.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class ScanService {

    private final ScanLogRepository scanLogRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public ScanResponse doScan(ScanRequest scanRequest) {
        OrderItem orderItem = orderItemRepository.findById(scanRequest.getOrderItemId())
                .orElseThrow(() -> new IllegalArgumentException("주문 항목이 존재하지 않습니다."));


        orderItem.addShippingQty(1);


        //스캔로그 -> 추후 바이어한테 제공할 정보
        ScanLog scanLog = ScanLog.builder()
                .orderItem(orderItem)
                .scannedAt(LocalDateTime.now())
                .scannedBy(scanRequest.getWorker())
                .build();

        ScanLog savedLog = scanLogRepository.save(scanLog);

        return


    }



}
