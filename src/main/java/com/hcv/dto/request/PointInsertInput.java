package com.hcv.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PointInsertInput extends PointBaseInput {

    @NotNull(message = "POINT_TYPE_INVALID")
    String type;

    @NotNull(message = "STUDENT_NOT_EXIST")
    String studentId;
}
