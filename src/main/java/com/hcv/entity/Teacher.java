package com.hcv.entity;

import com.hcv.config.StringListConverterConfig;
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

    @Convert(converter = StringListConverterConfig.class)
    @Column(name = "position", nullable = false)
    List<String> position = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    User users;

    @OneToMany(mappedBy = "teachers")
    List<Feedback> feedbacks = new ArrayList<>();

    @ManyToMany(mappedBy = "teachers")
    List<Research> researches = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "department_id")
    Department departments;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    Subject subjects;

    @ManyToMany(mappedBy = "teachers")
    List<Job> jobs = new ArrayList<>();

}
