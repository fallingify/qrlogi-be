package com.qrlogi.domain.orderitem.entity;


import com.qrlogi.domain.order.entity.Orders;

import com.qrlogi.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Id;

/**
 바이어가 주문한 상품 항목
 */
@Entity
@Table(name = "order_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int orderedQty;

    @Column(nullable = false)
    private int shippedQty;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus shipmentStatus;

    public void addShippingQty(int qty) {
        this.shippedQty += qty;
        updateShipmentStatus();
    }

    private void updateShipmentStatus() {
        if(shippedQty >= orderedQty) {this.shipmentStatus = ShipmentStatus.SHIPPED;}
        else if(shippedQty > 0) {this.shipmentStatus = ShipmentStatus.PARTIAL;}
        else { this.shipmentStatus = ShipmentStatus.PENDING;}
    }


    public int getQty() {
        return this.orderedQty;
    }


    public String getProductName() {
        return this.product.getName();
    }
}
