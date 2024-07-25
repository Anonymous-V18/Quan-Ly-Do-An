package com.hcv.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "department")
public class DepartmentEntity extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "departments")
    private List<SubjectEntity> subjects = new ArrayList<>();

    @OneToMany(mappedBy = "departments")
    private List<TeacherEntity> teachers = new ArrayList<>();

    @OneToMany(mappedBy = "departments")
    private List<StudentEntity> students = new ArrayList<>();

}
