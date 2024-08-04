package com.hcv.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserRequest extends BaseAuthInput {

    private List<String> nameRoles = new ArrayList<>();
    private Integer isGraduate = 0;

}
