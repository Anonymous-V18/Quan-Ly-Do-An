package com.hcv.entity;

import com.hcv.dto.StatusResearch;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "research")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Research extends BaseEntity {

    @Column(name = "name", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String name;
    @Column(name = "code", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String code;
    @Column(name = "detail", columnDefinition = "LONGTEXT COLLATE utf8mb4_unicode_ci")
    String detail;
    @Column(name = "notes", columnDefinition = "LONGTEXT COLLATE utf8mb4_unicode_ci")
    String notes;
    Integer maxMembers;
    Integer minMembers;
    String stage;
    String schoolYear;
    @Enumerated(EnumType.STRING)
    StatusResearch status;

    @OneToOne
    @JoinColumn(name = "group_id")
    Group group;

    @OneToMany(mappedBy = "research")
    List<Feedback> feedbacks = new ArrayList<>();

    @OneToMany(mappedBy = "research")
    List<ResearchTeacher> researchTeachers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "subject_id")
    Subject subject;

}
