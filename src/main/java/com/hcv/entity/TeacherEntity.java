package com.hcv.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "teacher")
@Data
public class TeacherEntity extends BaseEntity {

    @Column(name = "ma_so")
    private String maSo;
    @Column(name = "name")
    private String name;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "ngay_sinh")
    private Date DOB;
    @Column(name = "hoc_vi")
    private String hocVi;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "chuc_vu")
    private String chucVu;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity users;

    @OneToMany(mappedBy = "teachers")
    private List<FeedbackEntity> feedbacks = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "teacher_research"
            , joinColumns = @JoinColumn(name = "teacher_id")
            , inverseJoinColumns = @JoinColumn(name = "research_id"))
    private List<ResearchEntity> researches = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity departments;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subjects;

    @OneToMany(mappedBy = "teachers")
    private List<GroupEntity> groups = new ArrayList<>();


    @ManyToMany(mappedBy = "teachers")
    private List<JobEntity> jobs = new ArrayList<>();

}
