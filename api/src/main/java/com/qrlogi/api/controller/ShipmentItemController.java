package com.qrlogi.api.controller;

import com.qrlogi.domain.shippmentItem.dto.ShipmentItemRequest;
import com.qrlogi.domain.shippmentItem.dto.ShipmentItemResponse;
import com.qrlogi.domain.shippmentItem.service.ShipmentItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//주문상품별
@RestController
@RequestMapping("/api/v1/shipment")
@RequiredArgsConstructor
public class ShipmentItemController {

    private final ShipmentItemService shipmentItemService;


    @PostMapping
    public ResponseEntity<ShipmentItemResponse> createShipmentItem(@RequestBody ShipmentItemRequest shipmentItemRequest) {
        ShipmentItemResponse response =  shipmentItemService.createShipmentItem(shipmentItemRequest);
        return ResponseEntity.ok(response);
    }


}
