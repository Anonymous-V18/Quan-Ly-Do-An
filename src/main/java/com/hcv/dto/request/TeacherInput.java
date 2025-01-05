package com.hcv.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherInput {

    String userId;

    @NotNull(message = "CODE_INVALID")
    @Size(min = 8, message = "CODE_INVALID")
    String code;

    @NotNull(message = "NAME_INVALID")
    @Size(min = 4, message = "NAME_INVALID")
    String name;

    @NotNull(message = "DEGREE_INVALID")
    @Size(min = 4, message = "DEGREE_INVALID")
    String degree;

    @NotNull(message = "EMAIL_INVALID")
    @Email(message = "EMAIL_INVALID")
    String email;

    @NotNull(message = "PHONE_NUMBER_INVALID")
    @Size(min = 9, message = "PHONE_NUMBER_INVALID")
    String phoneNumber;

    Set<String> roleIds = new HashSet<>();

    @NotNull(message = "SUBJECT_ID_INVALID")
    @Size(min = 4, message = "SUBJECT_ID_INVALID")
    String subjectId;

}
