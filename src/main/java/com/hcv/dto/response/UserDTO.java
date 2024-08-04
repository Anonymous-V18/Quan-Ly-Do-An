package com.hcv.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends BaseDTO {

    private String username;

    @JsonIgnore
    private String password;

    private Integer isGraduate;

    private List<RoleDTO> roles = new ArrayList<>();

    private TeacherResponse teachers;

    private StudentResponse students;


}
