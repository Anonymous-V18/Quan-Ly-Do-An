package com.hcv.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "group")
public class GroupEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teachers;

    @OneToOne(mappedBy = "groups")
    private ResearchEntity researches;

    @OneToMany(mappedBy = "groups")
    private List<StudentEntity> students = new ArrayList<>();
}
