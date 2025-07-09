package com.qrlogi.domain.inspection.service;

import com.qrlogi.domain.document.entity.SheetRow;
import com.qrlogi.domain.document.repository.SheetRowRepository;
import com.qrlogi.domain.document.service.GoogleSheetService;
import com.qrlogi.domain.inspection.dto.ScanRequest;
import com.qrlogi.domain.inspection.dto.ScanResponse;
import com.qrlogi.domain.inspection.entity.ScanLog;
import com.qrlogi.domain.inspection.entity.ScanStatus;
import com.qrlogi.domain.inspection.repository.ScanLogRepository;
import com.qrlogi.domain.notification.dto.ScanCompletedEventDto;
import com.qrlogi.domain.notification.publisher.ScanCompletedPublisher;
import com.qrlogi.domain.orderitem.OrderItemValidator.OrderItemValidator;
import com.qrlogi.domain.orderitem.entity.OrderItem;
import com.qrlogi.domain.orderitem.entity.OrderItemSerial;
import com.qrlogi.domain.orderitem.repository.OrderItemSerialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
@RequiredArgsConstructor
public class ScanService {

    private final ScanLogRepository scanLogRepository;
    private final OrderItemValidator orderItemValidator;
    private final RedissonClient redissonClient;
    private final ScanCompletedPublisher publisher;
    private final OrderItemSerialRepository  orderItemSerialRepository;
    private final GoogleSheetService googleSheetService;
    private final SheetRowRepository sheetRowRepository;
    /**
     * doScan() 스캔 : 분산 락으로 동시에 같은 OrderItem lastQty, scannedQty 수정막음
     * Redisson 분산 락 적용, "scannedBy 스캔 > scannedQty" LOCK
     * kafka : Shipped 되면 이벤트 발생
     */
    @Transactional
    public ScanResponse doScan(ScanRequest scanRequest) {

        OrderItemSerial serialEntity = orderItemSerialRepository
                .findBySerial(scanRequest.getSerial())
                .orElseThrow(() -> new IllegalArgumentException("serial not found"));



        log.info("Trying to acquire lock for orderItemId: {}", scanRequest.getOrderItemId());
        String lockKey = "lock:scan:" + scanRequest.getOrderItemId();
        RLock lock = redissonClient.getLock(lockKey);

        boolean isAcquired = false;

        try {
            isAcquired = lock.tryLock(3, 10, TimeUnit.SECONDS);
            if (!isAcquired) {
                throw new IllegalStateException("scan is already in progress by another thread.");
            }
            log.info("Lock acquired for orderItemId: {}", scanRequest.getOrderItemId());
            OrderItem orderItem = orderItemValidator.validateByOrderItemIdExists(scanRequest.getOrderItemId());

            // 스캔 수량 + 1
            Integer lastQty = scanLogRepository.findMaxScannedQty(orderItem.getId());
            int scannedQty = (lastQty == null) ? 1 : lastQty + 1;

            // OrderItem의 출고 수량 증가
            orderItem.addShippingQty(1);


            //스캔로그 -> 추후 바이어한테 제공할 정보
            ScanLog scanLog = ScanLog.builder()
                    .orderItem(orderItem)
                    .productSerial(serialEntity.getSerial())
                    .scannedAt(LocalDateTime.now())
                    .scannedBy(scanRequest.getWorker())
                    .scannedQty(scannedQty)
                    .build();

            ScanLog savedLog = scanLogRepository.save(scanLog);

            //Sheet업로드
            SheetRow sheetRow = googleSheetService.getOrCreateSheetRow(serialEntity.getSerial());
            googleSheetService.appendRowBySerial(sheetRow, savedLog);
            sheetRow.doUpload();
            sheetRowRepository.save(sheetRow);

            //스캔 후 ShipmentStatus.SHIPPED 상태일 때만 Kafka 이벤트 발행
            publisher.publishScanCompleted(
                    ScanCompletedEventDto.toDTO(orderItem, scannedQty)
            );


            log.info("Scan completed for orderItemId: {}, scannedQty: {}", scanRequest.getOrderItemId(), scannedQty);
            return ScanResponse.toDTO(savedLog, ScanStatus.SUCCESS);

        } catch (InterruptedException e) { //인터럽트발생 -> 플래그 복원
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while trying to acquire the lock", e);

        } finally {
            if (isAcquired && lock.isHeldByCurrentThread()) { //사용후 반납
                log.info("Releasing lock for orderItemId: {}", scanRequest.getOrderItemId());
                lock.unlock();
            }
        }

    }



}
