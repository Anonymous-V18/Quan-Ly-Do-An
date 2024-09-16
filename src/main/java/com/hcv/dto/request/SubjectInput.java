package com.hcv.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubjectInput {

    @NotNull(message = "INVALID_SUBJECT_NAME_PARAM")
    @Size(min = 4, message = "INVALID_SUBJECT_NAME_PARAM")
    String name;
    @NotNull(message = "INVALID_DEPARTMENT_NAME_PARAM")
    @Size(min = 4, message = "INVALID_DEPARTMENT_NAME_PARAM")
    String nameDepartment;

}
