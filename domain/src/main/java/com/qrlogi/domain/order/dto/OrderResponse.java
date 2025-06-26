package com.qrlogi.domain.order.dto;

import com.qrlogi.domain.orderitem.dto.OrderItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderResponse {

    private String orderId;
    private String orderNumber;
    private String buyerName;
    private LocalDateTime orderTime;
    private String status;
    private List<OrderItemDTO> itemList;




}
