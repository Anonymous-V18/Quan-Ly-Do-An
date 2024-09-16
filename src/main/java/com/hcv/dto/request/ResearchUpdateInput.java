package com.hcv.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResearchUpdateInput {

    String name;
    String detail;
    String notes;
    Integer maxMembers;
    Integer minMembers;
    List<String> instructorsIds = new ArrayList<>();
    String thesisAdvisorId;
    String stage;
    String schoolYear;

}
