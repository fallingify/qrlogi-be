package com.qrlogi.domain.document.validator;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import com.qrlogi.domain.document.entity.SheetRow;
import com.qrlogi.domain.inspection.entity.ScanLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleSheetValidator {

    private final Sheets sheets;
    private final String RANGE = "A:F";
    @Value("${google.google-sheet.sheet-id}")
    private String sheetId;

    public void createSheetIfAbsent(String sheetName) {

        try {
            Spreadsheet newSheet = sheets.spreadsheets().get(sheetId).execute();

            boolean isExist = newSheet.getSheets().stream()
                    .anyMatch(s -> sheetName.equals(s.getProperties().getTitle()));

            if (!isExist) {
                SheetProperties properties = new SheetProperties().setTitle(sheetName);
                Request request = new Request().setAddSheet(new AddSheetRequest().setProperties(properties));
                BatchUpdateSpreadsheetRequest batch = new BatchUpdateSpreadsheetRequest().setRequests(List.of(request));

                sheets.spreadsheets().batchUpdate(sheetId, batch).execute();
                log.info("시트 '{}' 생성", sheetName);
            }
        } catch (IOException e) {
            log.error("시트 확인 생성 실패: {}", e.getMessage());
            throw new RuntimeException("Google 시트 생성 실패", e);
        }

    }

    public void validateAppendingRow(String sheetName, List<Object> row) {

        ValueRange record = new ValueRange().setValues(List.of(row));

        try {
            sheets.spreadsheets().values()
                    .append(sheetId, RANGE, record)
                    .setValueInputOption("RAW")
                    .setInsertDataOption("INSERT_ROWS")
                    .execute();
            log.info("시트에 ROW 추가 성공: {}",record);

        } catch (IOException e) {
            log.error("시트에 ROW 추가 실패 : {}", e.getMessage());
            throw new RuntimeException("시트 생성 실패 - 시트명: " + sheetName, e);
        }
    }

}
