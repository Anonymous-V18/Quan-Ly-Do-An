package com.hcv.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDTO extends BaseDTO<UserDTO> {

    private String username;

    private String password;

    private Integer isGraduate;

    List<RoleDTO> roles = new ArrayList<>();

}
