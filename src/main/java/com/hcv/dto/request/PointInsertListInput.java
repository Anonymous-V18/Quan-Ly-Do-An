package com.hcv.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PointInsertListInput {

    @NotNull(message = "POINT_TYPE_INVALID")
    String typePointId;

    @Valid
    List<PointInsertInput> points = new ArrayList<>();

}
