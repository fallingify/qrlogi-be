package com.qrlogi.domain.shippmentItem.repository;


import com.qrlogi.domain.shippmentItem.entity.ShipmentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShipmentItemRepository extends JpaRepository<ShipmentItem, Long> {

}
