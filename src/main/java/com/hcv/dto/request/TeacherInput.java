package com.hcv.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
public class TeacherInput {

    @Setter
    private String user_id;

    @NotNull(message = "INVALID_TEACHER_INPUT_PARAM")
    @Size(min = 8, message = "INVALID_TEACHER_INPUT_PARAM")
    private String maSo;

    @NotNull(message = "INVALID_TEACHER_INPUT_PARAM")
    @Size(min = 4, message = "INVALID_TEACHER_INPUT_PARAM")
    private String name;

    @NotNull(message = "INVALID_TEACHER_INPUT_PARAM")
    @Size(min = 4, message = "INVALID_TEACHER_INPUT_PARAM")
    private String hocVi;

    @NotNull(message = "INVALID_TEACHER_INPUT_PARAM")
    @Email(message = "INVALID_TEACHER_INPUT_PARAM")
    private String email;

    @NotNull(message = "INVALID_TEACHER_INPUT_PARAM")
    @Size(min = 8, message = "INVALID_TEACHER_INPUT_PARAM")
    private String phoneNumber;

    @NotNull(message = "INVALID_TEACHER_INPUT_PARAM")
    @Size(min = 4, message = "INVALID_TEACHER_INPUT_PARAM")
    private String chucVu;

    private String departmentName;

    @NotNull(message = "INVALID_TEACHER_INPUT_PARAM")
    @Size(min = 4, message = "INVALID_TEACHER_INPUT_PARAM")
    private String subjectName;

}
