package com.hcv.repository;

import com.hcv.dto.TypeNotification;
import com.hcv.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface INotificationRepository extends JpaRepository<NotificationEntity, String> {

    List<NotificationEntity> findBySendToAndType(String sendTo, TypeNotification type);

}
