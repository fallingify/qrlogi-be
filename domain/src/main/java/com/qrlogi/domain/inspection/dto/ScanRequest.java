package com.qrlogi.domain.inspection.dto;


import lombok.Getter;

@Getter
public class ScanRequest {

    private Long orderItemId;
    private int qty;
    private String worker;

}
