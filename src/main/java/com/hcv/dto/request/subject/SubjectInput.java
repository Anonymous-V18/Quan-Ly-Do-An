package com.hcv.dto.request.Subject;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SubjectInput {

    @NotNull(message = "INVALID_SUBJECT_NAME_PARAM")
    @Size(min = 4, message = "INVALID_SUBJECT_NAME_PARAM")
    private String name;
    @NotNull(message = "INVALID_DEPARTMENT_NAME_PARAM")
    @Size(min = 4, message = "INVALID_DEPARTMENT_NAME_PARAM")
    private String nameDepartment;

}
