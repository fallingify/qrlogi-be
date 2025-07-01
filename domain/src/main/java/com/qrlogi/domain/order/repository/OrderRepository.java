package com.qrlogi.domain.order.repository;

import com.qrlogi.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, String> {
    Optional<Orders> findByOrderNumber(String orderNumber);


    //삭제
    @Query("SELECT o FROM Orders o JOIN FETCH o.orderManager WHERE o.orderNumber = :orderNumber")
    Optional<Orders> findByOrderNumberWithManager(@Param("orderNumber") String orderNumber);

}
