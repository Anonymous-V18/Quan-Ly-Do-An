package com.hcv.dto.request.Point;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointBaseInput {

    @NotNull(message = "POINT_INVALID")
    private Double point;

}
