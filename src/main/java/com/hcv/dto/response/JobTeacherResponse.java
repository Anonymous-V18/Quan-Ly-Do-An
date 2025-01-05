package com.hcv.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobTeacherResponse {

    String id;
    Instant from;
    Instant due;
    String senderCode;
    String senderName;
    String name;
    String description;
    String detail;
    Integer quantityRequirement;

    List<JobTeacherDetailResponse> jobTeacherDetails = new ArrayList<>();
}
