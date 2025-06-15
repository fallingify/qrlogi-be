package com.qrlogi.domain.order.dto;

import lombok.Data;

@Data
public class OrderRequest {

    private Long buyerId;
    private List<OrderItemRequest> orderlist;


}
