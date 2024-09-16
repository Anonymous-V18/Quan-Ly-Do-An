package com.hcv.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentNormalUpdateInput {

    @NotNull(message = "EMAIL_INVALID")
    @Email(message = "EMAIL_INVALID")
    String email;

    @NotNull(message = "PHONE_NUMBER_INVALID")
    @Size(min = 9, message = "PHONE_NUMBER_INVALID")
    String phoneNumber;

}
