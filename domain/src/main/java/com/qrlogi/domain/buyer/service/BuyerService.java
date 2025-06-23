package com.qrlogi.domain.buyer.service;


import com.qrlogi.domain.buyer.dto.BuyerRequest;
import com.qrlogi.domain.buyer.dto.BuyerResponse;
import com.qrlogi.domain.buyer.entity.Buyer;

import com.qrlogi.domain.buyer.repository.BuyerRepository;
import com.qrlogi.domain.buyer.validator.BuyerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuyerService {

    private final BuyerRepository buyerRepository;
    private final BuyerValidator buyerValidator;

    public BuyerResponse registerBuyer(BuyerRequest buyerRequest) {

        buyerValidator.validateDuplicated(buyerRequest.getEmail(), buyerRequest.getBussinessNumber());


        Buyer newBuyer = Buyer.builder()
                .name(buyerRequest.getName())
                .email(buyerRequest.getEmail())
                .phone(buyerRequest.getPhone())
                .businessNumber(buyerRequest.getBussinessNumber())
                .build();

        Buyer savedBuyer = buyerRepository.save(newBuyer);

        return toResponse(savedBuyer);
    }

    private BuyerResponse toResponse(Buyer savedBuyer) {

        BuyerResponse response = new BuyerResponse();
        response.setName(savedBuyer.getName());
        response.setEmail(savedBuyer.getEmail());
        response.setPhone(savedBuyer.getPhone());
        response.setBusinessNumber(savedBuyer.getBusinessNumber());
        return response;
    }

    public List<BuyerResponse> getAllBuyers() {

        return buyerRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


}
