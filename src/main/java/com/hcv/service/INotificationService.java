package com.hcv.service;

import com.hcv.dto.request.NotificationTypeInvitationInsertInput;
import com.hcv.dto.request.NotificationTypeReplyInvitationInput;
import com.hcv.dto.response.NotificationResponse;

import java.util.List;

public interface INotificationService {

    void insertInvitation(NotificationTypeInvitationInsertInput invitationInsertInput);

    void replyInvitation(String id, NotificationTypeReplyInvitationInput replyInvitationInput);

    List<NotificationResponse> showAllInvitation();
}
