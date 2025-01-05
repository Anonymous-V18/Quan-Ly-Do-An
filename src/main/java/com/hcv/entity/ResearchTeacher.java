package com.hcv.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "research_teacher")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResearchTeacher {

    @EmbeddedId
    ResearchTeacherId id;

    @ManyToOne
    @JoinColumn(name = "type_teacher_id")
    TypeTeacher typeTeacher;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "research_id", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    Research research;

}
