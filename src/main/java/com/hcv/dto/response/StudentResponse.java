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
public class StudentResponse {
    String id;
    String code;
    String name;
    String myClass;
    String email;
    String phoneNumber;
    SubjectDTO subject;
    List<PointResponse> points = new ArrayList<>();
}
