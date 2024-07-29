package com.hcv.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "point")
@Getter
@Setter
@RequiredArgsConstructor
public class PointEntity extends BaseEntity {

    private Double point;
    private String type;
    private String teacherId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity students;

}
