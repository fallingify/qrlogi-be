package com.qrlogi.domain.buyer.dto;

import lombok.Data;

@Data
public class BuyerRequest {

    private String name;
    private String email;
    private String password;
    private String phone;
    private String bussinessNumber;

}
