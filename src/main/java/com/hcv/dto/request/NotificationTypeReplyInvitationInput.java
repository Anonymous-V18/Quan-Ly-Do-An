package com.hcv.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationTypeReplyInvitationInput {

    @NotNull(message = "STATUS_INVITATION_INVALID")
    String statusInvitation;

}
