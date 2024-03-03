package com.hcv.api_controller.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseAuthInput<T> {

    protected String username;
    protected String password;

}
