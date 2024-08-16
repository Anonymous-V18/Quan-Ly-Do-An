package com.hcv.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
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

    @NotNull(message = "POSITION_INVALID")
    @Size(min = 1, message = "POSITION_INVALID")
    List<String> position = new ArrayList<>();

    @NotNull(message = "SUBJECT_NAME_INVALID")
    @Size(min = 4, message = "SUBJECT_NAME_INVALID")
    String subjectName;

}
