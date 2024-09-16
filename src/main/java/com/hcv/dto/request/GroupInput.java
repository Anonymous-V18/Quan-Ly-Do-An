package com.hcv.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupInput {

    @NotNull(message = "INVALID_LIST_STUDENT")
    @Size(min = 1, message = "INVALID_LIST_STUDENT")
    List<String> studentIds = new ArrayList<>();

}
