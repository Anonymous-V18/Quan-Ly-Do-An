package com.hcv.dto.response;

import com.hcv.dto.CodeRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO extends BaseDTO {

    private String name;
    private CodeRole code;

}
