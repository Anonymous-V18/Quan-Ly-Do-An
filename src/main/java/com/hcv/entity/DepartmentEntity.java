package com.hcv.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "department")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentEntity extends BaseEntity {

    String name;

    @OneToMany(mappedBy = "departments")
    List<SubjectEntity> subjects = new ArrayList<>();

    @OneToMany(mappedBy = "departments")
    List<TeacherEntity> teachers = new ArrayList<>();

    @OneToMany(mappedBy = "departments")
    List<StudentEntity> students = new ArrayList<>();

}
