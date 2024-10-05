package com.hcv.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "point")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Point extends BaseEntity {

    Double point;
    String teacherId;

    @ManyToOne
    @JoinColumn(name = "type_point_id")
    TypePoint typePoint;

    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;

}
