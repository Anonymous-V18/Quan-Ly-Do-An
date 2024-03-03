package com.hcv.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class JobDTO extends BaseDTO<JobDTO> {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date from;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date due;
    private String sendTo;
    private String name;
    private String details;
    private Integer isCompleted;
    private List<TeacherDTO> teachers = new ArrayList<>();

}
