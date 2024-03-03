package com.hcv.api_controller.input;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SignInInput extends BaseAuthInput<SignInInput> {

    private List<String> nameRoles = new ArrayList<>();

}
