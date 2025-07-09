package com.qrlogi.domain.document.repository;

import com.qrlogi.domain.document.entity.SheetRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SheetRowRepository extends JpaRepository<SheetRow, Long> {
    Optional<SheetRow> findByOrderItemSerial_Serial(String serial);
}
