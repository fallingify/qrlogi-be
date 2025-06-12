package com.qrlogi.buyer.repository;


import com.qrlogi.buyer.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {

    boolean existsByEmail(String email);
    boolean existsByBusinessNumber(String username);
}
