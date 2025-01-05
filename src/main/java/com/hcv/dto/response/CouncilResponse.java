package com.hcv.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouncilResponse {

    String id;
    Instant createdDate;
    Instant modifiedDate;
    Boolean isActivated;
    SubjectResponse subject;
    Set<TeacherResponse> teachers = new HashSet<>();


}
