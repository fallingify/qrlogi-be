package com.qrlogi.api.controller;

import com.qrlogi.domain.inspection.dto.ScanRequest;
import com.qrlogi.domain.inspection.dto.ScanResponse;
import com.qrlogi.domain.inspection.service.ScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/scan")
@RequiredArgsConstructor
public class ScanController {

    private final ScanService scanService;


    @PostMapping
    public ResponseEntity<ScanResponse> scan(@RequestBody ScanRequest scanRequest) {
        ScanResponse response = scanService.doScan(scanRequest);
        return ResponseEntity.ok(response);
    }

}
