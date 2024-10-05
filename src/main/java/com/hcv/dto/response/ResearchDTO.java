package com.hcv.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResearchDTO {

    String id;
    String name;
    String code;
    String detail;
    String notes;
    Integer maxMembers;
    Integer minMembers;
    List<String> instructorsIds = new ArrayList<>();
    String thesisAdvisorId;
    String status;
    String stage;
    String schoolYear;
    Integer isApproved;

    List<TeacherResponse> teachers = new ArrayList<>();
    List<SubjectResponse> subjects = new ArrayList<>();
    GroupDTO group;
}
