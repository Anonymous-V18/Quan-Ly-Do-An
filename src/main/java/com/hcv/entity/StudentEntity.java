package com.hcv.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
@Getter
@Setter
@RequiredArgsConstructor
public class StudentEntity extends BaseEntity {

    private String maSo;
    private String name;
    private String myClass;
    private String email;
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity users;

    @OneToMany(mappedBy = "students")
    private List<FeedbackEntity> feedbacks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity groups;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subjects;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity departments;

    @ManyToMany
    @JoinTable(name = "student_job",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id"))
    private List<JobEntity> jobs = new ArrayList<>();

    @OneToMany(mappedBy = "students")
    private List<PointEntity> points = new ArrayList<>();

}
