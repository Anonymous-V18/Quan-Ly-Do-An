package com.hcv.dto.request.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseAuthInput {

    @NotNull(message = "INVALID_USERNAME")
    @Size(min = 8, message = "INVALID_USERNAME")
    protected String username;

    @NotNull(message = "INVALID_USERNAME")
    @Size(min = 8, message = "INVALID_PASSWORD")
    protected String password;

}
