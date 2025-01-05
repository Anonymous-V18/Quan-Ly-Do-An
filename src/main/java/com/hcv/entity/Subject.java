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

    @OneToMany(mappedBy = "subject")
    List<Teacher> teachers = new ArrayList<>();

    @OneToMany(mappedBy = "subject")
    List<Student> students = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "department_id")
    Department department;

    @OneToMany(mappedBy = "subject")
    List<Research> researches = new ArrayList<>();

    @OneToOne(mappedBy = "subject")
    Council council;

}
