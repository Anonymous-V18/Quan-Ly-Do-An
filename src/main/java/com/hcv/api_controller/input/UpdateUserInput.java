package com.hcv.api_controller.input;

import lombok.Getter;

@Getter
public class UpdateUserInput extends BaseAuthInput<UpdateUserInput> {
    private Integer isGraduate;
}
