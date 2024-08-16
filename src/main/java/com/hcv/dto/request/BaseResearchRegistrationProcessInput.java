package com.hcv.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
public class BaseResearchRegistrationProcessInput {

    @NotNull(message = "RESEARCHES_PARAM_INVALID")
    @Size(min = 8, message = "RESEARCHES_PARAM_INVALID")
    String researchID;
    
}
