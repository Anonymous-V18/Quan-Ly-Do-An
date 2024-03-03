package com.hcv.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "job")
@Data
public class JobEntity extends BaseEntity {
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "from")
    private Date from;
    @Column(name = "due")
    private Date due;
    @Column(name = "send_to")
    private String sendTo;
    @Column(name = "name")
    private String name;
    @Column(name = "details")
    private String details;
    @Column(name = "is_completed")
    private Integer isCompleted;

    @ManyToMany(mappedBy = "jobs")
    private List<TeacherEntity> teachers = new ArrayList<>();

    @ManyToMany(mappedBy = "jobs")
    private List<StudentEntity> students = new ArrayList<>();


}
