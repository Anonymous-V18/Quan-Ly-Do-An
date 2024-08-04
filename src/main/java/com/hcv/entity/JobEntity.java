package com.hcv.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "job")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobEntity extends BaseEntity {

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date from;
    Date due;
    String sendTo;
    String sendFrom;
    String name;
    String details;
    Integer isCompleted;

    @ManyToMany
    @JoinTable(name = "job_teacher"
            , joinColumns = @JoinColumn(name = "job_id")
            , inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    List<TeacherEntity> teachers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "group_id")
    GroupEntity groups;

}
