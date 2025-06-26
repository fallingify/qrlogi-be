package com.qrlogi.domain.notification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;


import java.time.LocalDateTime;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "notification_log")
public class NotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type; //Email, kakao 알림


    @Column(nullable = false)
    private String receiverName;

    @Column(nullable = false)
    private String receiverEmail;

    @Column(nullable = false)
    private String emailTitle;

    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    @Column(nullable = false)
    private LocalDateTime timestamp;

}
