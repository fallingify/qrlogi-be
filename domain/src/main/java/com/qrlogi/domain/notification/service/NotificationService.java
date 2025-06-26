package com.qrlogi.domain.notification.service;

import com.qrlogi.domain.notification.dto.NotificationRequest;
import com.qrlogi.domain.notification.entity.NotificationLog;
import com.qrlogi.domain.notification.entity.NotificationStatus;
import com.qrlogi.domain.notification.entity.NotificationType;
import com.qrlogi.domain.notification.repository.NotificationRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final JavaMailSender javaMailSender;

    public void sendEmailNotification(NotificationRequest request) {
        NotificationStatus status;

        try {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            helper.setTo(request.getReceiverEmail());
            helper.setSubject(request.getEmailTitle());
            helper.setText(request.getMessage(), false);


            javaMailSender.send(mimeMessage);
            log.info("Notification By Email #Receiver(Representative email: {}, Message: {}", request.getReceiverEmail(),  request.getMessage());
            status = NotificationStatus.SENT;

        } catch (Exception e) {
            log.error("sending email failed : {}", e.getMessage());
            status = NotificationStatus.FAILED;
        }


        NotificationLog logEntry = NotificationLog.builder()
                .type(NotificationType.EMAIL)
                .emailTitle(request.getEmailTitle())
                .message(request.getMessage())
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();

        notificationRepository.save(logEntry);

    }



}
