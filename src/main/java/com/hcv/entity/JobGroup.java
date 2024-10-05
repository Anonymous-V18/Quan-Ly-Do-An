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

import java.time.Instant;

@Entity
@Table(name = "job_group")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobGroup extends BaseEntity {

    Instant from;
    Instant due;
    String senderId;
    String name;
    String description;
    String detail;
    Integer isCompleted;

    @ManyToOne
    @JoinColumn(name = "group_id")
    Group group;
}
