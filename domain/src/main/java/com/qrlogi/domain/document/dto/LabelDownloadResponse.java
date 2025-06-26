package com.qrlogi.domain.document.dto;

import lombok.Builder;
import lombok.Getter;
import java.io.ByteArrayOutputStream;

@Getter
@Builder
public class LabelDownloadResponse {

    private String fileName;
    private long fileSize;
    private String contentType;


    public static LabelDownloadResponse toDTO(ByteArrayOutputStream outputStream, String fileName) {
        return LabelDownloadResponse.builder()
                .fileName(fileName)
                .fileSize(outputStream.size())
                .contentType("application/pdf")
                .build();
    }
}
