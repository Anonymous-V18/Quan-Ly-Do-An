package com.hcv.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationTypeInvitationInsertInput {

    Boolean isSendAllStudent = false;
    List<String> studentIds = new ArrayList<>();

}
