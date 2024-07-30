package com.hcv.dto.request.teacher;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
public class TeacherInput {

    @Setter
    private String userId;

    @NotNull(message = "MASO_INVALID")
    @Size(min = 8, message = "MASO_INVALID")
    private String maSo;

    @NotNull(message = "NAME_INVALID")
    @Size(min = 4, message = "NAME_INVALID")
    private String name;

    @NotNull(message = "HOCVI_INVALID")
    @Size(min = 4, message = "HOCVI_INVALID")
    private String hocVi;

    @NotNull(message = "EMAIL_INVALID")
    @Email(message = "EMAIL_INVALID")
    private String email;

    @NotNull(message = "PHONE_NUMBER_INVALID")
    @Size(min = 9, message = "PHONE_NUMBER_INVALID")
    private String phoneNumber;

    @NotNull(message = "POSITION_INVALID")
    @Size(min = 4, message = "POSITION_INVALID")
    private String chucVu;

    private String departmentName;

    @NotNull(message = "SUBJECT_NAME_INVALID")
    @Size(min = 4, message = "SUBJECT_NAME_INVALID")
    private String subjectName;

}
