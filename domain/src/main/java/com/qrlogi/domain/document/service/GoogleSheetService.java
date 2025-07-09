package com.qrlogi.domain.document.service;


import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import com.qrlogi.domain.document.entity.SheetRow;
import com.qrlogi.domain.document.repository.SheetRowRepository;
import com.qrlogi.domain.document.validator.GoogleSheetValidator;
import com.qrlogi.domain.inspection.entity.ScanLog;
import com.qrlogi.domain.orderitem.entity.OrderItemSerial;
import com.qrlogi.domain.orderitem.repository.OrderItemSerialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleSheetService {

    private final Sheets sheets;
    private final String RANGE = "A:F";
    private final GoogleSheetValidator googleSheetValidator;

    private final SheetRowRepository sheetRowRepository;
    private final OrderItemSerialRepository orderItemSerialRepository;


    public SheetRow getOrCreateSheetRow(String serial) {
        return sheetRowRepository.findByOrderItemSerial_Serial(serial)
                .orElseGet(() -> {
                    OrderItemSerial orderItemSerial = orderItemSerialRepository.findBySerial(serial)
                            .orElseThrow(() -> new IllegalArgumentException("OrderItemSerial not found for serial: " + serial));

                    SheetRow newRow = SheetRow.builder()
                            .orderItemSerial(orderItemSerial)
                            .sheetName(orderItemSerial.getOrderItem().getOrder().getOrderNumber())
                            .rowIndex(Integer.parseInt(String.valueOf(sheetRowRepository.count() + 2)))
                            .build();

                    return sheetRowRepository.save(newRow);
                });
    }




    @Value("${google.google-sheet.sheet-id}")
    private String sheetId;

    public void writeTestRow() {
        List<Object> row = List.of("TEST-123", "Test Product", "TP-001", "SERIAL", "scanned", "2025-07-07T18:00");
        ValueRange valueRange = new ValueRange().setValues(List.of(row));

        try {
            sheets.spreadsheets().values()
                    .append(sheetId, RANGE, valueRange)
                    .setValueInputOption("RAW")
                    .setInsertDataOption("INSERT_ROWS")
                    .execute();
            log.info("성공: {}", row);
        } catch (IOException e) {
            log.error("실패, Google API 오류 : {}", e.getMessage());
            throw new RuntimeException("Google Sheet 업로드 실패", e);
        }
    }

    public void appendRowBySerial(SheetRow sheetRow, ScanLog scanLog) {
        String sheetName = sheetRow.getSheetName();
        List<Object> record = sheetRow.toRecord(scanLog);

        googleSheetValidator.createSheetIfAbsent(sheetName);
        googleSheetValidator.validateAppendingRow(sheetName, record);

    }









}

