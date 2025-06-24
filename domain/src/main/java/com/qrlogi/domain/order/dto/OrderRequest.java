package com.qrlogi.domain.order.dto;

import com.qrlogi.domain.orderitem.dto.OrderItemRequest;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    private Long buyerId;
    private List<OrderItemRequest> orderlist;


}
