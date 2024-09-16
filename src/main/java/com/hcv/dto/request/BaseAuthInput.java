package com.hcv.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class BaseAuthInput {

    @NotNull(message = "INVALID_USERNAME")
    @Size(min = 8, message = "INVALID_USERNAME")
    String username;

    @NotNull(message = "INVALID_USERNAME")
    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;

}
