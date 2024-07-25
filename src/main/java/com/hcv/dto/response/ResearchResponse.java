package com.hcv.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hcv.dto.ResearchDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResearchResponse extends ResearchDTO {

    private List<FeedbackResponse> feedbacks = new ArrayList<>();

}
