package com.hcv.api_controller.input;

import lombok.Getter;

@Getter
public abstract class BaseAuthInput<T> {

    protected String username;
    protected String password;
}
