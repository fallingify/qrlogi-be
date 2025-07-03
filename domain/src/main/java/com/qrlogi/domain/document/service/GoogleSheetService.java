package com.qrlogi.domain.document.service;

import com.qrlogi.domain.document.dto.GoogleSheetDto;
import com.qrlogi.domain.orderitem.entity.OrderItemSerial;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 구글 시트 실시간 반영용
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleSheetService {

//    private final GoogleSheetClient webhookClient;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${webhook.google-sheet.url}")
    private String sheetUrl;

    /*
    스캔시 전송
     */
    public void send(OrderItemSerial serial) {
        GoogleSheetDto dto = GoogleSheetDto.from(serial, serial.getIsScanned());
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<GoogleSheetDto> entity = new HttpEntity<>(dto, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(sheetUrl, entity, String.class);


            log.info("Sending to sheet > response : {}", response.getBody());

        } catch (Exception e) {
            log.error("Sending googleSheet failed : {} ", e.getMessage());
        }
    }
    /*
    테이블 초기화
     */
    public void initializationBySerials(List<OrderItemSerial> serials) {
        for(OrderItemSerial serial : serials) {
            send(serial);
        }
    }




}
