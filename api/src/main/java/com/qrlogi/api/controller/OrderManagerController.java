package com.qrlogi.api.controller;

import com.qrlogi.domain.order.dto.OrderManagerRequest;
import com.qrlogi.domain.order.dto.OrderManagerResponse;
import com.qrlogi.domain.order.service.OrderManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/manager")
@RequiredArgsConstructor
public class OrderManagerController {

    private final OrderManagerService orderManagerService;

    //담당자등록
    @PostMapping("/register")
    public ResponseEntity<Long> registerManager(@RequestBody OrderManagerRequest orderManagerRequest) {

        Long managerId = orderManagerService.registerManager(orderManagerRequest);
        return ResponseEntity.ok(managerId);

    }

    //조회
    @GetMapping("/detail/{managerId}")
    public ResponseEntity<OrderManagerResponse> getManagerDetails(@PathVariable Long managerId) {

        return ResponseEntity.ok(orderManagerService.getManagerById(managerId));
    }

    //주문할당 및 담당자 변경
    @PatchMapping("/orders/{orderNumber}/manager/{managerId}")
    public ResponseEntity<Void> assignManager(@PathVariable String orderNumber, @PathVariable Long managerId) {

        orderManagerService.assignOrder(managerId, orderNumber);
        return ResponseEntity.ok().build();

    }









}
