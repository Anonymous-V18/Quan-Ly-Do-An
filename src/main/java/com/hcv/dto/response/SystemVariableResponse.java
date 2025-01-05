package com.hcv.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SystemVariableResponse {

    String id;
    Instant createdDate;
    Instant modifiedDate;
    String code;
    String name;
    String value;
    String description;

}
