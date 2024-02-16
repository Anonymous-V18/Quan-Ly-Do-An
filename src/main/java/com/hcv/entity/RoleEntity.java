package com.hcv.entity;

import com.hcv.dto.CodeRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    @Enumerated(EnumType.STRING)
    private CodeRole code;

    @ManyToMany(mappedBy = "roles")
    List<UserEntity> users = new ArrayList<>();

}

