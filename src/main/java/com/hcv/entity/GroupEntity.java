package com.hcv.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "group")
public class GroupEntity extends BaseEntity {

    @Column(name = "ma_so_gvhd")
    private String maSo_GVHD;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teachers;

    @OneToOne(mappedBy = "groups")
    private ResearchEntity researches;

    @OneToMany(mappedBy = "groups")
    private List<StudentEntity> students = new ArrayList<>();
}
