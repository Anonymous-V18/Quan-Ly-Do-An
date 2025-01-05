package com.hcv.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupResponse {

    String id;
    String leaderId;
    List<StudentResponse> students = new ArrayList<>();
    List<JobGroupShortenedResponse> jobGroups = new ArrayList<>();

}
