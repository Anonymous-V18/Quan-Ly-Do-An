package com.hcv.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "job")
@Getter
@Setter
@RequiredArgsConstructor
public class JobEntity extends BaseEntity {

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date from;
    private Date due;
    private String sendTo;
    private String sendFrom;
    private String name;
    private String details;
    private Integer isCompleted;

    @ManyToMany
    @JoinTable(name = "job_teacher"
            , joinColumns = @JoinColumn(name = "job_id")
            , inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private List<TeacherEntity> teachers = new ArrayList<>();

    //    @ManyToMany(mappedBy = "jobs")
//    private List<StudentEntity> students = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity groups;

}
