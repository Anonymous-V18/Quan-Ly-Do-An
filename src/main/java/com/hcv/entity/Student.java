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
@Table(name = "student")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student extends BaseEntity {

    @Column(name = "code", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String code;
    String name;
    String myClass;
    String email;
    String phoneNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    User users;

    @OneToMany(mappedBy = "students")
    List<Feedback> feedbacks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "group_id")
    Group groups;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    Subject subjects;

    @ManyToOne
    @JoinColumn(name = "department_id")
    Department departments;

    @OneToMany(mappedBy = "students")
    List<Point> points = new ArrayList<>();

    @OneToMany(mappedBy = "students")
    List<Notification> notifications = new ArrayList<>();

}
