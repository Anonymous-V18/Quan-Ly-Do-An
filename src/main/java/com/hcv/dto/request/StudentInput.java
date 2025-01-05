package com.hcv.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentInput {

    String userId;

    @NotNull(message = "CODE_INVALID")
    @Size(min = 8, message = "CODE_INVALID")
    String code;

    @NotNull(message = "NAME_INVALID")
    @Size(min = 4, message = "NAME_INVALID")
    String name;

    @NotNull(message = "MY_CLASS_INVALID")
    @Size(min = 4, message = "MY_CLASS_INVALID")
    String myClass;

    @NotNull(message = "EMAIL_INVALID")
    @Email(message = "EMAIL_INVALID")
    String email;

    @NotNull(message = "PHONE_NUMBER_INVALID")
    @Size(min = 9, message = "PHONE_NUMBER_INVALID")
    String phoneNumber;

    @NotNull(message = "SUBJECT_ID_INVALID")
    @Size(min = 4, message = "SUBJECT_ID_INVALID")
    String subjectId;

}
