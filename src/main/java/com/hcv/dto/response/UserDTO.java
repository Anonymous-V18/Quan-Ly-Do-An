package com.hcv.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    String id;
    String username;
    @JsonIgnore
    String password;
    Integer isGraduate;

    List<RoleDTO> roles = new ArrayList<>();

    TeacherResponse teachers;

    StudentResponse students;


}
