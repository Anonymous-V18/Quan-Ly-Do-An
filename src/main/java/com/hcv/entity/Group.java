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
public class Group extends BaseEntity {

    Integer minMember;
    Integer maxMember;
    String leaderId;

    @OneToOne(mappedBy = "group")
    Research research;

    @OneToMany(mappedBy = "group")
    List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    List<JobGroup> jobGroups = new ArrayList<>();

}
