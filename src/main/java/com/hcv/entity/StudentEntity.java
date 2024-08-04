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
@Table(name = "student")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentEntity extends BaseEntity {

    String maSo;
    String name;
    String myClass;
    String email;
    String phoneNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    UserEntity users;

    @OneToMany(mappedBy = "students")
    List<FeedbackEntity> feedbacks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "group_id")
    GroupEntity groups;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    SubjectEntity subjects;

    @ManyToOne
    @JoinColumn(name = "department_id")
    DepartmentEntity departments;

    @OneToMany(mappedBy = "students")
    List<PointEntity> points = new ArrayList<>();

}
