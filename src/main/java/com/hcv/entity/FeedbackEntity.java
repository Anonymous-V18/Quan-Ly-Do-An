package com.hcv.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "feedback")
public class FeedbackEntity extends BaseEntity {

    private String message;
    private String sendTo;
    private String sendFrom;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teachers;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity students;

    @ManyToOne
    @JoinColumn(name = "research_id")
    private ResearchEntity researches;

}
