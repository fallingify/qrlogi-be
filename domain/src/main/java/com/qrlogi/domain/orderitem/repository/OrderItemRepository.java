package com.qrlogi.domain.orderitem.repository;

import com.qrlogi.domain.order.entity.Orders;
import com.qrlogi.domain.orderitem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllByOrder(Orders order);


}
