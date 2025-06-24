package com.qrlogi.domain.inspection.service;

import com.qrlogi.domain.inspection.dto.ScanRequest;
import com.qrlogi.domain.inspection.dto.ScanResponse;
import com.qrlogi.domain.inspection.entity.ScanLog;
import com.qrlogi.domain.inspection.entity.ScanStatus;
import com.qrlogi.domain.inspection.repository.ScanLogRepository;
import com.qrlogi.domain.orderitem.OrderItemValidator.OrderItemValidator;
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
    private final OrderItemValidator orderItemValidator;

    //TODO : Redisson 분산 락 적용, scannedBy 스캔 > scannedQty 은 Lock을 걸어야 한다.
    @Transactional
    public ScanResponse doScan(ScanRequest scanRequest) {

        OrderItem orderItem = orderItemValidator.validateByOrderItemIdExists(scanRequest.getOrderItemId());

        // 스캔 수량 + 1
        Integer lastQty = scanLogRepository.findMaxScannedQty(orderItem.getId());
        int scannedQty = (lastQty == null) ? 1 : lastQty + 1;


        // OrderItem의 출고 수량 증가
        orderItem.addShippingQty(1);


        //스캔로그 -> 추후 바이어한테 제공할 정보
        ScanLog scanLog = ScanLog.builder()
                .orderItem(orderItem)
                .scannedAt(LocalDateTime.now())
                .scannedBy(scanRequest.getWorker())
                .scannedQty(scannedQty)
                .build();

        ScanLog savedLog = scanLogRepository.save(scanLog);

        return ScanResponse.toDTO(savedLog, ScanStatus.SUCCESS);

    }



}
