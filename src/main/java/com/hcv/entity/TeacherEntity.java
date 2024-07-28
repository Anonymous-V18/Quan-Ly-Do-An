package com.hcv.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teacher")
@Getter
@Setter
@RequiredArgsConstructor
public class TeacherEntity extends BaseEntity {

    private String maSo;
    private String name;
    private String hocVi;
    private String email;
    private String phoneNumber;
    private String chucVu;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity users;

    @OneToMany(mappedBy = "teachers")
    private List<FeedbackEntity> feedbacks = new ArrayList<>();

    @ManyToMany(mappedBy = "teachers")
    private List<ResearchEntity> researches = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity departments;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subjects;

    @ManyToMany(mappedBy = "teachers")
    private List<JobEntity> jobs = new ArrayList<>();

}
