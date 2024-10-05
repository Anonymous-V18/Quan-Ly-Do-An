package com.hcv.dto.request;

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
public class JobTeacherInput extends BaseJobInput {

    @NotNull(message = "INVALID_QUANTITY_REQUIREMENT_PARAM")
    Integer quantityRequirement;

    @NotNull
    List<String> teacherIds = new ArrayList<>();
}
