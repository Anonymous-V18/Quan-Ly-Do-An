package com.hcv.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackResponse extends BaseDTO {

    private String message;
    private String sendTo;
    private String sendFrom;

}
