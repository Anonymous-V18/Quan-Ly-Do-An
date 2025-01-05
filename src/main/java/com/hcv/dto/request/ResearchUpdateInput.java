package com.hcv.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResearchUpdateInput {

    String name;
    String detail;
    String notes;
    Integer maxMembers;
    Integer minMembers;
    String subInstructorId;

}
