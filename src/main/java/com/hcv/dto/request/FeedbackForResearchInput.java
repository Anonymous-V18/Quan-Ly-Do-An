package com.hcv.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackForResearchInput {

    @NotNull(message = "MESSAGE_PARAM_FEEDBACK_INVALID")
    @Size(min = 2, message = "MESSAGE_PARAM_FEEDBACK_INVALID")
    String message;
    @NotNull(message = "RESEARCHES_PARAM_INVALID")
    @Size(min = 8, message = "RESEARCHES_PARAM_INVALID")
    String researchID;

}
