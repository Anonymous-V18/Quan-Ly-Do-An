package com.hcv.dto.request.Student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
public class StudentInput {

    @Setter
    private String user_id;

    @NotNull(message = "MASO_INVALID")
    @Size(min = 8, message = "MASO_INVALID")
    private String maSo;

    @NotNull(message = "NAME_INVALID")
    @Size(min = 4, message = "NAME_INVALID")
    private String name;

    @NotNull(message = "MY_CLASS_INVALID")
    @Size(min = 4, message = "MY_CLASS_INVALID")
    private String myClass;

    @NotNull(message = "EMAIL_INVALID")
    @Email(message = "EMAIL_INVALID")
    private String email;

    @NotNull(message = "PHONE_NUMBER_INVALID")
    @Size(min = 9, message = "PHONE_NUMBER_INVALID")
    private String phoneNumber;

    private String departmentName;

    @NotNull(message = "SUBJECT_NAME_INVALID")
    @Size(min = 4, message = "SUBJECT_NAME_INVALID")
    private String subjectName;

}
