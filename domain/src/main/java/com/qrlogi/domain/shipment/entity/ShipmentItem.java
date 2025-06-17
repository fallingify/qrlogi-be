package com.qrlogi.domain.shipment.entity;


import com.qrlogi.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

/**
 실제 출고된 상품 항목
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "shipment_items")
public class ShipmentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="shipment_id", nullable = false)
    private Shipments shipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int qty;

}
