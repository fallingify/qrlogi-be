package com.qrlogi.domain.inspection.service;

import com.qrlogi.domain.inspection.repository.ScanLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScanService {

    private final ScanLogRepository scanLogRepository;

}
