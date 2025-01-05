package com.hcv.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class ResearchTeacherId implements Serializable {

    @Column(name = "teacher_id", nullable = false)
    String teacherId;

    @Column(name = "research_id", nullable = false)
    String researchId;

}
