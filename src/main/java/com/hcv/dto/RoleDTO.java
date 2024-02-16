package com.hcv.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO extends BaseDTO<RoleDTO> {

    private String name;
    private CodeRole code;

}
