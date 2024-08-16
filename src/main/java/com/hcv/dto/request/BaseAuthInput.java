package com.hcv.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
public class BaseAuthInput {

    @NotNull(message = "INVALID_USERNAME")
    @Size(min = 8, message = "INVALID_USERNAME")
    String username;

    @NotNull(message = "INVALID_USERNAME")
    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;

}
