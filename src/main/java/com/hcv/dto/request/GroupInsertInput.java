package com.hcv.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupInsertInput {

    @Min(value = 2, message = "INVALID_MIN_MEMBER_PARAM")
    Integer minMember = 2;
    @Min(value = 3, message = "INVALID_MAX_MEMBER_PARAM")
    Integer maxMember = 3;

}
