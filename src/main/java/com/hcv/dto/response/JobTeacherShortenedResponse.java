package com.hcv.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobTeacherShortenedResponse {

    String id;
    Instant from;
    Instant due;
    String senderCode;
    String senderName;
    String name;
    String description;
    Integer quantityRequirement;
    Integer totalQuantityRequired;
    Integer quantityCompleted;

}
