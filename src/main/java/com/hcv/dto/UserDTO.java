package com.hcv.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends BaseDTO<UserDTO> {

    private String username;

    private String password;

    private Integer isGraduate;

    private List<RoleDTO> roles = new ArrayList<>();

    private TeacherDTO teachers;

    private StudentDTO students;


}
