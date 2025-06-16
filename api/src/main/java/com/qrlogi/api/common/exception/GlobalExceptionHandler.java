package com.qrlogi.api.common.exception;

import com.qrlogi.api.common.dto.CommonResponse;
import com.qrlogi.api.common.dto.ErrorInfo;
import com.qrlogi.api.common.enums.ReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<ErrorInfo>> handleGeneralException(Exception ex) {

        String traceId = UUID.randomUUID().toString();
        ReturnCode returnCode = ReturnCode.UNKNOWN_ERROR;
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String errMsg = (ex.getMessage() != null) ? ex.getMessage() : "예외 메시지 없음";

        if (ex instanceof BusinessException be) {
            returnCode =    be.getErrorCode();
            httpStatus = be.getErrorCode().getHttpStatus();
        } else if (ex instanceof org.springframework.web.bind.MethodArgumentNotValidException) {
            returnCode = ReturnCode.VALIDATION_ERROR;
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        } else if (ex instanceof org.springframework.web.server.ResponseStatusException rse) {
            httpStatus =  HttpStatus.resolve(rse.getStatusCode().value());
            if (httpStatus == null) httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            returnCode = ReturnCode.BAD_REQUEST;
        }


        log.error("[traceId: {}] {}: {}", traceId, ex.getClass().getSimpleName(), errMsg, ex);

        ErrorInfo errorInfo = getErrorInfo(ex, errMsg, httpStatus, traceId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Trace-Id", traceId);

        return ResponseEntity.status(httpStatus)
                .headers(httpHeaders)
                .body(CommonResponse.of(returnCode, errorInfo));

    }


    private static ErrorInfo getErrorInfo(Exception ex, String errMsg, HttpStatus httpStatus, String traceId) {
        return ErrorInfo.builder()
                .exception(ex.getClass().getSimpleName())
                .errorMessage(errMsg)
                .status(httpStatus.value())
                .traceId(traceId)
                .build();

    }


}
