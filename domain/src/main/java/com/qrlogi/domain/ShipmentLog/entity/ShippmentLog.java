package com.qrlogi.domain.ShipmentLog.entity;

import com.qrlogi.domain.shipmentitem.entity.ShipmentItem;
import com.qrlogi.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "qr_scan_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ShippmentLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderItemId;

    private LocalDateTime scannedAt;

    private int scannedQty;

    private String scannedBy; //작업자이름




}
