package com.qrlogi.domain.order.entity;


import com.qrlogi.domain.buyer.entity.Buyer;
import com.qrlogi.domain.orderitem.entity.OrderItem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Orders {

    private static int QuantityLimit = 100;

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

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_manager_id")
    private OrderManager orderManager;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void cancel() {
        this.orderStatus = OrderStatus.CANCELLED;
    }

    /**
     * a. 100건 초과 시 결제 요청((결제 필요 여부 파악)
     */
    public boolean isRequirePayment(){
        int orderItemSumQty = orderItems
                .stream().mapToInt(OrderItem::getOrderedQty)
                .sum();

        return orderItemSumQty > QuantityLimit;
    }

}