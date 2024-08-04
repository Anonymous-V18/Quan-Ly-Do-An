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
@Table(name = "feedback")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackEntity extends BaseEntity {

    String message;
    String sendTo;
    String sendFrom;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    TeacherEntity teachers;

    @ManyToOne
    @JoinColumn(name = "student_id")
    StudentEntity students;

    @ManyToOne
    @JoinColumn(name = "research_id")
    ResearchEntity researches;

}
