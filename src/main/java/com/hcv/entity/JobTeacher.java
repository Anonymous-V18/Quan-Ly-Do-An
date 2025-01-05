package com.hcv.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "job_teacher")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobTeacher extends BaseEntity {

    Instant from;
    Instant due;
    String senderCode;
    String senderName;
    String name;
    String description;
    @Column(name = "detail", columnDefinition = "LONGTEXT COLLATE utf8mb4_unicode_ci")
    String detail;
    String type;
    Integer quantityRequirement;

    @OneToMany(mappedBy = "jobTeacher")
    List<JobTeacherDetail> jobTeacherDetails = new ArrayList<>();

}

