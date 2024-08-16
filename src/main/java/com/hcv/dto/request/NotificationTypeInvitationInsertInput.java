package com.hcv.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationTypeInvitationInsertInput {

    Boolean isSendAllStudent;
    List<String> studentIds = new ArrayList<>();

}
