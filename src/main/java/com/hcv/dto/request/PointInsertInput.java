package com.hcv.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PointInsertInput extends BasePointInput {

    @NotNull(message = "STUDENT_NOT_EXIST")
    String studentId;

}
