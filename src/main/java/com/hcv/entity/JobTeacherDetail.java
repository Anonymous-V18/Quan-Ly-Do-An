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
@Table(name = "job_teacher_detail")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobTeacherDetail extends BaseEntity {

    Integer quantityCompleted;
    Integer isCompleted;

    @ManyToOne
    @JoinColumn(name = "job_id")
    JobTeacher jobTeacher;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    Teacher teacher;

}
