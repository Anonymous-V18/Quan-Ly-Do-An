package com.hcv.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "research")
@Data
public class ResearchEntity extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "ma_de_tai")
    private String maDeTai;
    @Column(name = "detail")
    private String detail;
    @Column(name = "notes")
    private String notes;
    @Column(name = "max_members")
    private Integer maxMembers;
    @Column(name = "min_members")
    private Integer minMembers;
    @Column(name = "ma_so_gvhd")
    private String maSo_GVHD;
    @Column(name = "ma_so_gvpb")
    private String maSo_GVPB;

    @ManyToMany(mappedBy = "researches")
    private List<TeacherEntity> teachers = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "feedback_id")
    private FeedbackEntity feedbacks;

    @ManyToMany
    @JoinTable(name = "research_subject"
            , joinColumns = @JoinColumn(name = "research_id")
            , inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<SubjectEntity> subjects = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "group_id")
    private GroupEntity groups;

}
