package com.qrlogi.domain.shipment.entity;


import com.qrlogi.domain.buyer.entity.Buyer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "shipments")
public class Shipment {

    @Id
    @Column(length = 36)
    private String id; // UUID

    @Column(nullable = false, unique = true)
    private Long shipmentNum; //내부식별자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private Buyer buyer;

    @Column(nullable = false)
    private String shipmentCode;

    @Column(nullable = false)
    private LocalDateTime shipmentDate;

    @PrePersist
    public void generateId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
}
