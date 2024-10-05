package com.hcv.dto.response;

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
public class TeacherDTO {

    String id;
    String code;
    String name;
    String degree;
    String email;
    String phoneNumber;
    List<String> position = new ArrayList<>();
    SubjectDTO subject;

}
