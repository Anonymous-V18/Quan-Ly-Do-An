package com.hcv.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupEntity extends BaseEntity {

    @OneToOne(mappedBy = "groups")
    ResearchEntity researches;

    @OneToMany(mappedBy = "groups")
    List<StudentEntity> students = new ArrayList<>();

    @OneToMany(mappedBy = "groups")
    List<JobEntity> jobs = new ArrayList<>();

}
