package com.hcv.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hcv.dto.BaseDTO;
import com.hcv.dto.SubjectDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResearchResponse extends BaseDTO {

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

    private List<TeacherResponse> teachers = new ArrayList<>();
    private List<SubjectDTO> subjects = new ArrayList<>();
    private List<FeedbackResponse> feedbacks = new ArrayList<>();
    private GroupResponse groups;

}
