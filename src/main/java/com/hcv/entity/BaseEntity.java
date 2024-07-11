package com.hcv.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected String id;

    @Column(name = "createdby")
    @CreatedBy
    protected String createdBy;

    @Column(name = "createddate")
    @CreatedDate
    protected Date createdDate;

    @Column(name = "modifiedby")
    @LastModifiedBy
    protected String modifiedBy;

    @Column(name = "modifieddate")
    @LastModifiedDate
    protected Date modifiedDate;

}
