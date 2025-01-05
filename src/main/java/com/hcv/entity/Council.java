package com.hcv.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "council")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Council extends BaseEntity {

    @OneToMany(mappedBy = "council")
    Set<Teacher> teachers = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "subject_id")
    Subject subject;

    Boolean isActivated;
}
