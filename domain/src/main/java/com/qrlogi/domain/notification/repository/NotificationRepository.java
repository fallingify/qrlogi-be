package com.qrlogi.domain.notification.repository;

import com.qrlogi.domain.notification.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationLog, Long> {
}
