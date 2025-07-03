package com.qrlogi.api.controller;

import com.qrlogi.domain.document.service.GoogleSheetService;
import com.qrlogi.domain.order.entity.Orders;
import com.qrlogi.domain.order.validator.OrderValidator;
import com.qrlogi.domain.orderitem.entity.OrderItemSerial;
import com.qrlogi.domain.orderitem.repository.OrderItemSerialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sheets")
@RequiredArgsConstructor
public class GoogleSheetController {


    private final OrderItemSerialRepository orderItemSerialRepository;
    private final OrderValidator orderValidator;
    private final GoogleSheetService googleSheetService;

    @Value("${webhook.google-sheet.url}")
    private String sheetUrl;

    @PostMapping("/build/{orderNumber}")
    public ResponseEntity<String> buildSheet(@PathVariable String orderNumber){
        Orders order = orderValidator.validateByOrderNumber(orderNumber);
        List<OrderItemSerial> serials = orderItemSerialRepository.findAllByOrderItem_Order(order);

        String message = String.format(
                "%d processed.\n" +
                        "Click the following link.\n" +
                        "[Google Sheet Link] : %s",
                serials.size(),
                sheetUrl
        );

        return ResponseEntity.ok(message);
    }



}
