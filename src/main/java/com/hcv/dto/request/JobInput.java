package com.hcv.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobInput {

    @NotNull(message = "INVALID_JOB_FOR_TEACHER_PARAM")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    Date from;
    @NotNull(message = "INVALID_JOB_FOR_TEACHER_PARAM")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    Date due;
    @NotNull(message = "INVALID_JOB_FOR_TEACHER_PARAM")
    @Size(min = 8, message = "INVALID_JOB_FOR_TEACHER_PARAM")
    String sendTo;
    @NotNull(message = "INVALID_JOB_FOR_TEACHER_PARAM")
    @Size(min = 8, message = "INVALID_JOB_FOR_TEACHER_PARAM")
    String name;
    @NotNull(message = "INVALID_JOB_FOR_TEACHER_PARAM")
    @Size(min = 8, message = "INVALID_JOB_FOR_TEACHER_PARAM")
    String details;

}
