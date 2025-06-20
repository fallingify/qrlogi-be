package com.qrlogi.api.controller;

import com.qrlogi.domain.orderitem.dto.OrderItemDTO;
import com.qrlogi.domain.orderitem.dto.OrderItemSerialResponse;
import com.qrlogi.domain.orderitem.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/orderItems")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    //보기
    @GetMapping("/{orderId}")
    public List<OrderItemDTO> getOrderItems(@PathVariable("orderId") String orderId)  {
        return orderItemService.getOrderItems(orderId);

    }

    //생성
    @PostMapping("/{orderId}/serials")
    public List<OrderItemSerialResponse> createSerial(@PathVariable("orderId") String orderId) {
        return orderItemService.generateSerials(orderId);
    }



}
