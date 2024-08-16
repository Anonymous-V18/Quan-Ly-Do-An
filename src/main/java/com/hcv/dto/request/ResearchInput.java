package com.hcv.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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
    Integer maxMembers;
    @NotNull(message = "MIN_MEMBER_PRAM_RESEARCH_INVALID")
    @Min(value = 1, message = "MIN_MEMBER_PRAM_RESEARCH_INVALID")
    @Max(value = 4, message = "MIN_MEMBER_PRAM_RESEARCH_INVALID")
    Integer minMembers;
    List<String> instructorsIds = new ArrayList<>();
    @NotNull(message = "STAGE_PARAM_RESEARCH_INVALID")
    @Min(value = 1, message = "STAGE_PARAM_RESEARCH_INVALID")
    String stage;
    @NotNull(message = "SCHOOL_YEAR_PARAM_RESEARCH_INVALID")
    @Size(min = 4, message = "SCHOOL_YEAR_PARAM_RESEARCH_INVALID")
    String schoolYear;


}
