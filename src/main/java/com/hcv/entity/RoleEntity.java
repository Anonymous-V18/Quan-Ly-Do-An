package com.hcv.entity;

import com.hcv.dto.CodeRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
@Getter
@Setter
@RequiredArgsConstructor
public class RoleEntity extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private CodeRole code;

    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> users = new ArrayList<>();

}

