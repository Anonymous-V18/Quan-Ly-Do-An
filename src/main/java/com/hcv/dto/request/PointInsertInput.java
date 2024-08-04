package com.hcv.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointInsertInput extends PointBaseInput {

    @NotNull(message = "POINT_TYPE_INVALID")
    private String type;

    @NotNull(message = "STUDENT_NOT_EXIST")
    private String studentId;
}
