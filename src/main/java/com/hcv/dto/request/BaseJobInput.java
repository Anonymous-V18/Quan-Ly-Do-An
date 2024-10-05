package com.hcv.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class BaseJobInput {

    @NotNull(message = "INVALID_FROM_PARAM")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]X")
    Instant from;

    @NotNull(message = "INVALID_DUE_PARAM")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]X")
    Instant due;

    @NotNull(message = "INVALID_NAME_PARAM")
    @Size(min = 8, message = "INVALID_NAME_PARAM")
    String name;

    String description;

    @NotNull(message = "INVALID_DETAIL_PARAM")
    @Size(min = 8, message = "INVALID_DETAIL_PARAM")
    String detail;
}
