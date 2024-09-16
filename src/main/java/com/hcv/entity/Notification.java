package com.hcv.entity;

import com.hcv.dto.StatusNotification;
import com.hcv.dto.TypeNotification;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "notification")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification extends BaseEntity {

    String message;
    @Column(name = "send_to", nullable = false)
    String sendTo;
    @Column(name = "send_from", nullable = false)
    String sendFrom;
    @Enumerated(EnumType.STRING)
    TypeNotification type;
    @Enumerated(EnumType.STRING)
    StatusNotification status;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    Teacher teachers;

    @ManyToOne
    @JoinColumn(name = "student_id")
    Student students;

    @ManyToOne
    @JoinColumn(name = "research_id")
    Research researches;

}
