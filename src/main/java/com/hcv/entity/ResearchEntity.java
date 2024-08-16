package com.hcv.entity;

import com.hcv.config.StringListConverterConfig;
import com.hcv.dto.StatusResearch;
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

    @Column(name = "name", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String name;
    @Column(name = "code", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String code;
    String detail;
    String notes;
    Integer maxMembers;
    Integer minMembers;
    @Convert(converter = StringListConverterConfig.class)
    @Column(name = "instructors_ids", nullable = false)
    List<String> instructorsIds = new ArrayList<>();
    String thesisAdvisorId;
    String stage;
    String schoolYear;
    @Enumerated(EnumType.STRING)
    StatusResearch status;


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
