package com.hcv.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "research")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResearchEntity extends BaseEntity {

    String name;
    String maDeTai;
    String detail;
    String oldDetail;
    String notes;
    Integer maxMembers;
    Integer minMembers;
    String gvhd;
    String gvpb;
    String dotDangKy;
    String namHoc;
    Integer isApproved;


    @ManyToMany
    @JoinTable(name = "research_teacher"
            , joinColumns = @JoinColumn(name = "research_id")
            , inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    List<TeacherEntity> teachers = new ArrayList<>();

    @OneToMany(mappedBy = "researches")
    List<FeedbackEntity> feedbacks = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "research_subject"
            , joinColumns = @JoinColumn(name = "research_id")
            , inverseJoinColumns = @JoinColumn(name = "subject_id"))
    List<SubjectEntity> subjects = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "group_id")
    GroupEntity groups;

}
