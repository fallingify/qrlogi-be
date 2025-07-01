package com.qrlogi.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class OrderManagerResponse {
    private Long id;
    private String managerName;
    private String managerEmail;
    private List<String> orderlist;
}
