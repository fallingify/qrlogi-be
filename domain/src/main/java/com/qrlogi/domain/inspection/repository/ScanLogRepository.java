package com.qrlogi.domain.inspection.repository;

import com.qrlogi.domain.inspection.entity.ScanLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanLogRepository extends JpaRepository<ScanLog, Long> {

    @Query("SELECT COUNT(sl) FROM ScanLog sl WHERE sl.orderItem.id = :orderItemId")
    long countByOrderItemId(@Param("orderItemId") Long orderItemId);

}
