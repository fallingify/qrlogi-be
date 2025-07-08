package com.qrlogi.domain.document.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class SheetRowResponse {

    private String sheetName;
    private String serial;
    private boolean uploaded;
    private LocalDateTime uploadedAt;
}
