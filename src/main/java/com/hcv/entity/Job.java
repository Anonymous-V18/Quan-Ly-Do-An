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
@Table(name = "job")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Job extends BaseEntity {


    String sendTo;
    String sendFrom;
    String name;
    Integer isCompleted;

    @ManyToMany
    @JoinTable(name = "job_teacher"
            , joinColumns = @JoinColumn(name = "job_id")
            , inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    List<Teacher> teachers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "group_id")
    Group groups;

}
