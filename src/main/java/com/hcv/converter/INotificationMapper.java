package com.hcv.converter;

import com.hcv.dto.response.NotificationResponse;
import com.hcv.entity.Notification;
import org.mapstruct.Mapper;

@Mapper
public interface INotificationMapper {

    NotificationResponse toShowDTO(Notification notification);

}
