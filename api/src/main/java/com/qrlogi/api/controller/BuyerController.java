package com.qrlogi.api.controller;


import com.qrlogi.domain.buyer.dto.BuyerRequest;
import com.qrlogi.domain.buyer.dto.BuyerResponse;
import com.qrlogi.domain.buyer.service.BuyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buyers")
@RequiredArgsConstructor
public class BuyerController {

    private final BuyerService buyerService;

    @PostMapping
    public ResponseEntity<BuyerResponse> registerBuyer(@RequestBody BuyerRequest buyerRequest) {
        return ResponseEntity.ok(buyerService.registerBuyer(buyerRequest));

    }

    @GetMapping
    public ResponseEntity<List<BuyerResponse>> getAllBuyers() {
        return ResponseEntity.ok(buyerService.getAllBuyers());
    }

}
