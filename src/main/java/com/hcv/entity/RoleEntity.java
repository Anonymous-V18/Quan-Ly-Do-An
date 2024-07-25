package com.hcv.entity;

import com.hcv.dto.CodeRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private CodeRole code;

    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> users = new ArrayList<>();

}

