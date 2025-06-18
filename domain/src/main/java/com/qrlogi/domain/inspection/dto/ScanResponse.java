package com.qrlogi.domain.inspection.dto;

import com.qrlogi.domain.inspection.entity.ScanLog;
import com.qrlogi.domain.inspection.entity.ScanStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScanResponse {

    private Long logId;
    private Long orderItemId;
    private String productName;
    private int scannedQty;
    private String scannedBy;
    private String shipmentStatus;
    private LocalDateTime scannedAt;
    private ScanStatus scanStatus;

    public static ScanResponse toDTO(ScanLog scanLog, ScanStatus scanStatus) {
        return new ScanResponse(
                scanLog.getId(),
                scanLog.getOrderItem().getId(),
                scanLog.getOrderItem().getProduct().getName(),
                scanLog.getScannedQty(),
                scanLog.getScannedBy(),
                scanLog.getOrderItem().getShipmentStatus().name(),
                scanLog.getScannedAt(),
                scanStatus

        );
    }

}
