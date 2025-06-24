package com.qrlogi.domain.inspection.entity;

import com.qrlogi.domain.orderitem.entity.OrderItem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "qr_scan_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ScanLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id", nullable = false)
    private OrderItem orderItem;

    @Column(nullable = false)
    private LocalDateTime scannedAt;

    @Column(nullable = false)
    private int scannedQty;

    @Column(nullable = false)
    private String scannedBy; //작업자이름

    @Column(nullable = false)
    private String productSerial;




}
