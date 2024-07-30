package com.hcv.dto.request.research;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResearchInput {

    @NotNull(message = "NAME_PARAM_RESEARCH_INVALID")
    @Size(min = 3, message = "NAME_PARAM_RESEARCH_INVALID")
    private String name;
    private String maDeTai;
    @NotNull(message = "DETAIL_PARAM_RESEARCH_INVALID")
    @Size(min = 3, message = "DETAIL_PARAM_RESEARCH_INVALID")
    private String detail;
    private String oldDetail;
    private String notes;
    @NotNull(message = "MAX_MEMBER_PRAM_RESEARCH_INVALID")
    @Min(value = 1, message = "MAX_MEMBER_PRAM_RESEARCH_INVALID")
    @Max(value = 4, message = "MAX_MEMBER_PRAM_RESEARCH_INVALID")
    private Integer maxMembers;
    @NotNull(message = "MIN_MEMBER_PRAM_RESEARCH_INVALID")
    @Min(value = 1, message = "MIN_MEMBER_PRAM_RESEARCH_INVALID")
    @Max(value = 4, message = "MIN_MEMBER_PRAM_RESEARCH_INVALID")
    private Integer minMembers;
    @NotNull(message = "GVHD_PARAM_RESEARCH_INVALID")
    @Size(min = 8, message = "GVHD_PARAM_RESEARCH_INVALID")
    private String gvhd;
    @NotNull(message = "GVPB_PARAM_RESEARCH_INVALID")
    @Size(min = 8, message = "GVPB_PARAM_RESEARCH_INVALID")
    private String gvpb;
    @NotNull(message = "DOTDANGKY_PARAM_RESEARCH_INVALID")
    @Min(value = 1, message = "DOTDANGKY_PARAM_RESEARCH_INVALID")
    private String dotDangKy;
    @NotNull(message = "NAMHOC_PARAM_RESEARCH_INVALID")
    @Size(min = 4, message = "NAMHOC_PARAM_RESEARCH_INVALID")
    private String namHoc;
    private Integer isApproved = 0;

    @NotNull(message = "SUBJECTS_PARAM_RESEARCH_INVALID")
    private List<String> subjectsID = new ArrayList<>();
}
