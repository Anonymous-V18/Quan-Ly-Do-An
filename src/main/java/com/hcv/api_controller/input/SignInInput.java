package com.hcv.api_controller.input;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SignInInput extends BaseAuthInput<SignInInput> {

    private List<String> nameRoles = new ArrayList<>();

}
