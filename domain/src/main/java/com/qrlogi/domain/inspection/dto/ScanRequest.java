package com.qrlogi.domain.inspection.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScanRequest {

    private Long orderItemId;
    private String worker;
    //스캔1번당 +1qty

}
