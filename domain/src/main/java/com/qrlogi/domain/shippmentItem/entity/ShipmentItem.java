package com.qrlogi.domain.shippmentItem.entity;

import com.qrlogi.domain.orderitem.entity.OrderItem;
import com.qrlogi.domain.product.entity.Product;
import com.qrlogi.domain.shipment.entity.Shipment;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shipment_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ShipmentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipments_id", nullable = false)
    private Shipment shipment;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_item_id", nullable = false)
    private OrderItem orderItem;

    @Column(nullable = false)
    private int qty;



}
