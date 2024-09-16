package com.hcv.repository;

import com.hcv.dto.TypeNotification;
import com.hcv.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, String> {

    List<Notification> findBySendToAndType(String sendTo, TypeNotification type);

}
