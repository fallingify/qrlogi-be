package com.qrlogi.domain.order.dto;

import com.qrlogi.orderitem.orderitem.dto.OrderItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {

    private String orderId;
    private Long orderNumber;
    private String buyerName;
    private LocalDateTime orderTime;
    private String status;
    private List<OrderItemDTO> itemList;




}
