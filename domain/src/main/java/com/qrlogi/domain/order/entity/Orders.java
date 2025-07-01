package com.qrlogi.domain.order.entity;


import com.qrlogi.domain.buyer.entity.Buyer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Orders {

    @Id
    @Column(length = 36)
    private String id; // UUID

    @Column(nullable = false, unique = true)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private Buyer buyer;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    //주문 후 나중에 담당자 배정 -> nullable
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_manager_id")
    private OrderManager orderManager;


    public void cancel() {
        this.orderStatus = OrderStatus.CANCELLED;
    }

}