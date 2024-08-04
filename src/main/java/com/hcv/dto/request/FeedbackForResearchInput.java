package com.hcv.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class FeedbackForResearchInput {

    @NotNull(message = "MESSAGE_PARAM_FEEDBACK_INVALID")
    @Size(min = 2, message = "MESSAGE_PARAM_FEEDBACK_INVALID")
    private String message;
    private String sendTo;
    private String sendFrom;
    @NotNull(message = "RESEARCHES_PARAM_FEEDBACK_INVALID")
    @Size(min = 8, message = "RESEARCHES_PARAM_FEEDBACK_INVALID")
    private String researchID;

}
