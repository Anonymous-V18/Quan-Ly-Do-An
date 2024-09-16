package com.hcv.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest extends BaseAuthInput {

    List<String> nameRoles = new ArrayList<>();
    Integer isGraduate = 0;

}
