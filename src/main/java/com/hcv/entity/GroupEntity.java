package com.hcv.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group")
@Getter
@Setter
@RequiredArgsConstructor
public class GroupEntity extends BaseEntity {

    @OneToOne(mappedBy = "groups")
    private ResearchEntity researches;

    @OneToMany(mappedBy = "groups")
    private List<StudentEntity> students = new ArrayList<>();

    @OneToMany(mappedBy = "groups")
    private List<JobEntity> jobs = new ArrayList<>();

}
