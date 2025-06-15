package com.qrlogi.domain.buyer.dto;

import lombok.Data;

@Data
public class BuyerResponse {

    private String name;
    private String email;
    private String phone;
    private String businessNumber;

}