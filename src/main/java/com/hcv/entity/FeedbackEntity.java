package com.hcv.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "feedback")
public class FeedbackEntity extends BaseEntity {

    @Column(name = "message")
    private String message;
    @Column(name = "send_to")
    private String sendTo;
    @Column(name = "send_from")
    private String sendFrom;
    @Column(name = "is_read")
    private String is_Read;


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
