package com.qrlogi.domain.buyer.validator;

import com.qrlogi.domain.buyer.repository.BuyerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuyerValidator {

    private final BuyerRepository buyerRepository;

    public void validateDuplicated(String email, String businessNumber) {

        if(buyerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email in use");
        }

        if (buyerRepository.existsByBusinessNumber(businessNumber)) {
            throw new IllegalArgumentException("Business-Num in use");

        }


    }

}
