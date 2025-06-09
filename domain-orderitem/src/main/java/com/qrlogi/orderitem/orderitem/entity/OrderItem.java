package com.qrlogi.orderitem.orderitem.entity;


import com.qrlogi.order.entity.Orders;
import com.qrlogi.product.entity.Product;
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

    @Column(name ="qty", nullable = false)
    private int qty;


}
