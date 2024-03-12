package com.hcv.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseAuthInput<T> {

    @NotNull
    @Size(min = 8, message = "INVALID_USERNAME")
    protected String username;
    @NotNull
    @Size(min = 8, message = "INVALID_PASSWORD")
    protected String password;

}
