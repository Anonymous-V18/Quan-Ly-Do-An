package com.hcv.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResearchInput {

    @NotNull(message = "NAME_PARAM_RESEARCH_INVALID")
    @Size(min = 3, message = "NAME_PARAM_RESEARCH_INVALID")
    String name;

    String code;

    @NotNull(message = "DETAIL_PARAM_RESEARCH_INVALID")
    @Size(min = 3, message = "DETAIL_PARAM_RESEARCH_INVALID")
    String detail;

    String notes;

    @NotNull(message = "MAX_MEMBER_PRAM_RESEARCH_INVALID")
    @Min(value = 1, message = "MAX_MEMBER_PRAM_RESEARCH_INVALID")
    @Max(value = 4, message = "MAX_MEMBER_PRAM_RESEARCH_INVALID")
    Integer maxMembers = 2;

    @NotNull(message = "MIN_MEMBER_PRAM_RESEARCH_INVALID")
    @Min(value = 1, message = "MIN_MEMBER_PRAM_RESEARCH_INVALID")
    @Max(value = 4, message = "MIN_MEMBER_PRAM_RESEARCH_INVALID")
    Integer minMembers = 3;

    String subInstructorId;

}
