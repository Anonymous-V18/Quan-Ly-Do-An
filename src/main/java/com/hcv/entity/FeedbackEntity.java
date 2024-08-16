package com.hcv.entity;

import com.hcv.dto.StatusNotification;
import jakarta.persistence.*;
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
    @Enumerated(EnumType.STRING)
    StatusNotification status;

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
