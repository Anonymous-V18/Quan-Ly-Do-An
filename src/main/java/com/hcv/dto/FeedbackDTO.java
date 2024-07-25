package com.hcv.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackDTO extends BaseDTO {

    private String message;
    private String sendTo;
    private String sendFrom;

    private TeacherDTO teachers;
    private StudentDTO students;
    private ResearchDTO researches;

}
