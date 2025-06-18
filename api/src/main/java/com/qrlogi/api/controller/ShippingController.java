package com.qrlogi.api.controller;

import com.qrlogi.domain.shipment.dto.ShippingRequest;
import com.qrlogi.domain.shipment.dto.ShippingResponse;
import com.qrlogi.domain.shipment.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shipping")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingService shippingService;

    @PostMapping("/qc")
    public ResponseEntity<ShippingResponse> confirmShipping(@RequestBody ShippingRequest shippingRequest) {
        ShippingResponse response = shippingService.doShipping(shippingRequest);
        return ResponseEntity.ok(response);

    }




}
