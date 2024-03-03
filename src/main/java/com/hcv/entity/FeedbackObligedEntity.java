package com.hcv.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "feedback_obliged")
public class FeedbackObligedEntity extends BaseEntity {

    @Column(name = "message")
    private String message;
    @Column(name = "send_to")
    private String sendTo;
    @Column(name = "send_from")
    private String sendFrom;
    @Column(name = "is_read")
    private String is_Read;
    @Column(name = "obliged_research")
    private String obligedResearch;

}
