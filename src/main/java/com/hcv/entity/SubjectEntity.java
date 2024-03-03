package com.hcv.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "subject")
public class SubjectEntity extends BaseEntity {
    @Column(name = "name")
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
