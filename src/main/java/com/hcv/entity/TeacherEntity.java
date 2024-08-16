package com.hcv.entity;

import com.hcv.config.StringListConverterConfig;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teacher")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherEntity extends BaseEntity {

    String code;
    String name;
    String degree;
    String email;
    String phoneNumber;
    @Convert(converter = StringListConverterConfig.class)
    @Column(name = "position", nullable = false)
    List<String> position = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    UserEntity users;

    @OneToMany(mappedBy = "teachers")
    List<FeedbackEntity> feedbacks = new ArrayList<>();

    @ManyToMany(mappedBy = "teachers")
    List<ResearchEntity> researches = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "department_id")
    DepartmentEntity departments;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    SubjectEntity subjects;

    @ManyToMany(mappedBy = "teachers")
    List<JobEntity> jobs = new ArrayList<>();

}
