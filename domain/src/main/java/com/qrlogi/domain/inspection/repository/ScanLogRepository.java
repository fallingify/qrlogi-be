package com.qrlogi.domain.inspection.repository;

import com.qrlogi.domain.inspection.entity.ScanLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScanLogRepository extends JpaRepository<ScanLog, Long> {

    @Query("SELECT MAX(sl.scannedQty) FROM ScanLog sl WHERE sl.orderItem.id = :orderItemId")
    Integer findMaxScannedQty(@Param("orderItemId") Long orderItemId);


    Optional<ScanLog> findByProductSerial(String serial);

}
