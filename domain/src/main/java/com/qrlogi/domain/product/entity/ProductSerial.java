package com.qrlogi.domain.product.entity;

import com.qrlogi.domain.orderitem.entity.OrderItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "product_serials")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSerial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;


    @Column(nullable = false, unique = true)
    private String serial;

    @Column(nullable = false)
    private boolean isScanned;


    public void scanned() {
        this.isScanned = true;
    }


}
