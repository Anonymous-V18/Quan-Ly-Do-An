package com.hcv.dto.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseAuthInput<T> {

    protected String username;
    protected String password;

}
