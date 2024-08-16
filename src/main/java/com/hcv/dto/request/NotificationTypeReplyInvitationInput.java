package com.hcv.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationTypeReplyInvitationInput {

    @NotNull(message = "STATUS_INVITATION_INVALID")
    String statusInvitation;

}
