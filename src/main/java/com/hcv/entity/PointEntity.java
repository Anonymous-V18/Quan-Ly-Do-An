package com.hcv.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "point")
@Data
public class PointEntity extends BaseEntity {

    @Column(name = "point")
    private Double point;
    @Column(name = "type")
    private String type;
    @Column(name = "ma_so_gv")
    private String maSo_GV;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity students;

}
