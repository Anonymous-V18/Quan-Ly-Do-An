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
public class Subject extends BaseEntity {

    String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    Department departments;

    @ManyToMany(mappedBy = "subjects")
    List<Research> researches = new ArrayList<>();

    @OneToMany(mappedBy = "subjects")
    List<Teacher> teachers = new ArrayList<>();

    @OneToMany(mappedBy = "subjects")
    List<Student> students = new ArrayList<>();

}
