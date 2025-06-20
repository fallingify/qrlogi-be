package com.qrlogi.domain.orderitem.repository;


import com.qrlogi.domain.orderitem.entity.OrderItemSerial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemSerialRepository  extends JpaRepository<OrderItemSerial, Long> {
    boolean existsBySerial(String serial);

}
