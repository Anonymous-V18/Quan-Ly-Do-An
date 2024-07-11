package com.hcv.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDTO extends BaseDTO {

    @Size(min = 3, message = "INVALID_NAME_PARAM")
    private String name;

}
