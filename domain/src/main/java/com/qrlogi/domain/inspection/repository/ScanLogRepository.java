package com.qrlogi.domain.inspection.repository;

import com.qrlogi.domain.inspection.entity.ScanLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface ScanLogRepository extends JpaRepository<ScanLog, Long> {
}
