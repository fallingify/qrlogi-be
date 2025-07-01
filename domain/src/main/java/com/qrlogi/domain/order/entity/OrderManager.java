package com.qrlogi.domain.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//TODO : Order당 매니저1명 매핑을 위한 엔티티추가, Orders에 필드추가 필요
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "order_manager")
public class OrderManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String managerName;

    @Column(nullable = false)
    private String managerEmail;

    @Builder.Default
    @OneToMany(mappedBy = "orderManager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orders> orderList = new ArrayList<>(); //manager 1: order N

    public void addOrder(Orders order) {
        orderList.add(order);
        order.setOrderManager(this);
    }







}
