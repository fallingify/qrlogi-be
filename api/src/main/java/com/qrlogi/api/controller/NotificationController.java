package com.qrlogi.api.controller;

import com.qrlogi.domain.notification.dto.NotificationRequest;
import com.qrlogi.domain.notification.producer.NotificationRequestProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notify")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRequestProducer producer;


    @PostMapping("/mailer")
    public ResponseEntity<String> publishEmailNotification(@RequestBody NotificationRequest notificationRequest){

        producer.send(notificationRequest);
        return ResponseEntity.ok("Notification(email) sent successfully");
    }


}
