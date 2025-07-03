package com.qrlogi.domain.document.client;

import com.qrlogi.domain.document.dto.GoogleSheetDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "GoogleSheetClient", url = "${webhook.google-sheet.url}")
public interface  GoogleSheetClient {

    @PostMapping(consumes = "application/json", headers = "Content-Type=application/json")
    void sendToSheet(@RequestBody GoogleSheetDto dto);
}
