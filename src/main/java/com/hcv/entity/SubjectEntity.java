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
@Table(name = "subject")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubjectEntity extends BaseEntity {

    String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    DepartmentEntity departments;

    @ManyToMany(mappedBy = "subjects")
    List<ResearchEntity> researches = new ArrayList<>();

    @OneToMany(mappedBy = "subjects")
    List<TeacherEntity> teachers = new ArrayList<>();

    @OneToMany(mappedBy = "subjects")
    List<StudentEntity> students = new ArrayList<>();

}
