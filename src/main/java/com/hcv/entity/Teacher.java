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
@Table(name = "teacher")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Teacher extends BaseEntity {

    @Column(name = "code", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String code;
    String name;
    String degree;
    String email;
    String phoneNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "teacher")
    List<JobTeacherDetail> jobTeacherDetails = new ArrayList<>();

    @OneToMany(mappedBy = "teacher")
    List<Feedback> feedbacks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "department_id")
    Department department;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    Subject subject;

    @ManyToOne
    @JoinColumn(name = "council_id")
    Council council;

    @OneToMany(mappedBy = "teacher")
    List<ResearchTeacher> researchTeachers = new ArrayList<>();

}
