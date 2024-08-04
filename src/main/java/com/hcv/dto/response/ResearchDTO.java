package com.hcv.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResearchDTO extends BaseDTO {

    private String name;
    private String maDeTai;
    private String detail;
    private String oldDetail;
    private String notes;
    private Integer maxMembers;
    private Integer minMembers;
    private String gvhd;
    private String gvpb;
    private String dotDangKy;
    private String namHoc;
    private Integer isApproved;

    private List<TeacherDTO> teachers = new ArrayList<>();
    private List<SubjectDTO> subjects = new ArrayList<>();
    private GroupDTO groups;
}
