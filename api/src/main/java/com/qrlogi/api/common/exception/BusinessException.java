package com.qrlogi.api.common.exception;

import com.qrlogi.api.common.enums.ReturnCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class BusinessException extends RuntimeException{

    private final ReturnCode errorCode;
 }