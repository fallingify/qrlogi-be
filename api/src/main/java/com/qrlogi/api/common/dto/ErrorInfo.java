package com.qrlogi.api.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorInfo {
    private String exception;
    private String errorMessage;
    private int status;
    private String traceId;

}
