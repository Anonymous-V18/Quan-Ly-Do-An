package com.hcv.dto.request.Job;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Date;

@Getter
public class JobInput {

    @NotNull(message = "INVALID_JOB_FOR_TEACHER_PARAM")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date from;
    @NotNull(message = "INVALID_JOB_FOR_TEACHER_PARAM")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date due;
    @NotNull(message = "INVALID_JOB_FOR_TEACHER_PARAM")
    @Size(min = 8, message = "INVALID_JOB_FOR_TEACHER_PARAM")
    private String sendTo;
    private String sendFrom;
    @NotNull(message = "INVALID_JOB_FOR_TEACHER_PARAM")
    @Size(min = 8, message = "INVALID_JOB_FOR_TEACHER_PARAM")
    private String name;
    @NotNull(message = "INVALID_JOB_FOR_TEACHER_PARAM")
    @Size(min = 8, message = "INVALID_JOB_FOR_TEACHER_PARAM")
    private String details;
    @NotNull(message = "INVALID_JOB_FOR_TEACHER_PARAM")
    private Integer isCompleted;


}
