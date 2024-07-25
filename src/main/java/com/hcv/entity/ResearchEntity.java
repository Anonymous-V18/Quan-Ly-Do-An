package com.hcv.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "research")
@Getter
@Setter
@RequiredArgsConstructor
public class ResearchEntity extends BaseEntity {

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


    @ManyToMany
    @JoinTable(name = "research_teacher"
            , joinColumns = @JoinColumn(name = "research_id")
            , inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private List<TeacherEntity> teachers = new ArrayList<>();

    @OneToMany(mappedBy = "researches")
    private List<FeedbackEntity> feedbacks = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "research_subject"
            , joinColumns = @JoinColumn(name = "research_id")
            , inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<SubjectEntity> subjects = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "group_id")
    private GroupEntity groups;

}
