package com.hcv.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subject")
@Getter
@Setter
@RequiredArgsConstructor
public class SubjectEntity extends BaseEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity departments;

    @ManyToMany(mappedBy = "subjects")
    private List<ResearchEntity> researches = new ArrayList<>();

    @OneToMany(mappedBy = "subjects")
    private List<TeacherEntity> teachers = new ArrayList<>();

    @OneToMany(mappedBy = "subjects")
    private List<StudentEntity> students = new ArrayList<>();

}
