package com.hcv.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResearchResponse {

    String id;
    String name;
    String code;
    String detail;
    String notes;
    Integer maxMembers;
    Integer minMembers;
    String stage;
    String schoolYear;
    String status;

    SubjectResponse subject;
    List<ResearchTeacherResponse> researchTeachers = new ArrayList<>();
    List<FeedbackResponse> feedbacks = new ArrayList<>();
    GroupResponse group;

}
