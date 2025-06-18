package com.qrlogi.domain.inspection.dto;

import com.qrlogi.domain.inspection.entity.ScanLog;
import com.qrlogi.domain.inspection.entity.ScanStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScanResult {

    private ScanResponse scanResponse;
    private ScanStatus scanStatus;

    public static ScanResult of(ScanLog log, ScanStatus status) {
        return new ScanResult(
                ScanResponse.toDTO(log, status),
                status
        );
    }
}
