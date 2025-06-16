package com.qrlogi.domain.order.dto;

import com.qrlogi.domain.orderitem.orderitem.dto.OrderItemRequest;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private Long buyerId;
    private List<OrderItemRequest> orderlist;


}
