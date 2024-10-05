package com.hcv.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobTeacherUpdateInput extends BaseJobInput {

    @NotNull(message = "INVALID_QUANTITY_REQUIREMENT_PARAM")
    Integer quantityRequirement;
    
}
