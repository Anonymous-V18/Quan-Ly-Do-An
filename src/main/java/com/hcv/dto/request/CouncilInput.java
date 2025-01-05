package com.hcv.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouncilInput {
    Set<String> teacherIds = new HashSet<>();
    String subjectId;
    Boolean isActivated = true;
}
