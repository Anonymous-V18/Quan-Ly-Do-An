package com.hcv.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherDTO {

    String id;
    String code;
    String name;
    String degree;
    String email;
    String phoneNumber;
    SubjectDTO subject;
    UserResponse user;

}
