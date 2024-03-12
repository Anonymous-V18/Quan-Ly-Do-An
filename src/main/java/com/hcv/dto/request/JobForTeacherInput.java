package com.hcv.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class JobForTeacherInput {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date from;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date due;
    private String sendTo;
    private String name;
    private String details;
    private Integer isCompleted;
    private List<String> listMaSoGV = new ArrayList<>();

}
