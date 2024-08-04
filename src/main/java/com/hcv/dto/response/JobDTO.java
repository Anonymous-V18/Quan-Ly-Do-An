package com.hcv.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class JobDTO extends BaseDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date from;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date due;
    private String sendTo;
    private String sendFrom;
    private String name;
    private String details;
    private Integer isCompleted = 0;
    private List<TeacherDTO> teachers = new ArrayList<>();

}
