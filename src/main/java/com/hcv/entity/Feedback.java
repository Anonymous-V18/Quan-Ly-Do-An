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
public class Feedback extends BaseEntity {

    String message;
    String sendFrom;
    String sendFromName;
    @Enumerated(EnumType.STRING)
    StatusNotification status;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;

    @ManyToOne
    @JoinColumn(name = "research_id")
    Research research;

}
