package com.hcv.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectDTO extends BaseDTO {

    private String name;
    private DepartmentDTO departments;

}
