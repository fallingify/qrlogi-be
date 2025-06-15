package com.qrlogi.domain.buyer.repository;


import com.qrlogi.domain.buyer.entity.Buyer;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends ListCrudRepository<Buyer, Long> {

    boolean existsByEmail(String email);
    boolean existsByBusinessNumber(String username);
}
