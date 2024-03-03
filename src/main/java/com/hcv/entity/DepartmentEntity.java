package com.hcv.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "department")
public class DepartmentEntity extends BaseEntity {
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "departments")
    private List<SubjectEntity> subjects = new ArrayList<>();

    @OneToMany(mappedBy = "departments")
    private List<TeacherEntity> teachers = new ArrayList<>();

    @OneToMany(mappedBy = "departments")
    private List<StudentEntity> students = new ArrayList<>();

}
