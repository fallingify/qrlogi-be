package com.qrlogi.domain.orderitem.repository;


import com.qrlogi.domain.order.entity.Orders;
import com.qrlogi.domain.orderitem.entity.OrderItemSerial;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemSerialRepository  extends JpaRepository<OrderItemSerial, Long> {

    @EntityGraph(attributePaths = {"orderItem", "orderItem.order"})
    List<OrderItemSerial> findAllByOrderItem_Order(Orders order);

}
