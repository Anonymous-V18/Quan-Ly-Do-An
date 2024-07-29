package com.hcv.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointDTO extends BaseDTO {

    private Double point;
    private String type;
    private String teacherId;
    private StudentDTO students;
    
}
