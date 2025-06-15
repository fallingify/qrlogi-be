package com.qrlogi.domain.buyer.service;


import com.qrlogi.domain.buyer.dto.BuyerRequest;
import com.qrlogi.domain.buyer.dto.BuyerResponse;
import com.qrlogi.domain.buyer.entity.Buyer;

import com.qrlogi.domain.buyer.repository.BuyerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuyerService {

    private final BuyerRepository buyerRepository;

    public BuyerResponse registerBuyer(BuyerRequest buyerRequest) {
        if(buyerRepository.existsByEmail(buyerRequest.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        if(buyerRepository.existsByBusinessNumber(buyerRequest.getBussinessNumber())){
            throw new IllegalArgumentException("Business number already in use");
        }

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
