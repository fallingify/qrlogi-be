package com.qrlogi.domain.notification.dto;


import com.qrlogi.domain.notification.entity.NotificationType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationRequest {

    private NotificationType type;
    private String receiverName;
    private String receiverEmail;
    private String emailTitle;
    private String message;

    public static NotificationRequest ofEmail(String receiverName, String receiverEmail, String emailTitle, String message) {
        return NotificationRequest.builder()
                .type(NotificationType.EMAIL)
                .receiverName(receiverName)
                .receiverEmail(receiverEmail)
                .emailTitle(emailTitle)
                .message(message)
                .build();

    }
}
