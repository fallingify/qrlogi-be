package com.qrlogi.domain.qrscanlog.entity;

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
public class QrScanLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_item_id", nullable = false)
    private ShipmentItem shipmentItem; //어떤 출고아이템인지

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;//스캔한 사람

    @Column(nullable = false)
    private String qrCode; // 스캔된 qr 문자열

    @Column(nullable = false)
    private LocalDateTime scanTime; //스캔 시각


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ScanStatus scanStatus; //성공실패




}
