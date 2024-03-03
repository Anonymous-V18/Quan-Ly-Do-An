package com.hcv.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "student")
@Data
public class StudentEntity extends BaseEntity {
    @Column(name = "ma_so")
    private String maSo;
    @Column(name = "name")
    private String name;
    @Column(name = "class")
    private String myClass;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
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
