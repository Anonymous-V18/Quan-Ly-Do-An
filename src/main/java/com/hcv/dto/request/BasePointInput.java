package com.hcv.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class BasePointInput {

    @NotNull(message = "POINT_INVALID")
    @Min(value = 0, message = "POINT_INVALID")
    @Max(value = 10, message = "POINT_INVALID")
    Double point;

}
