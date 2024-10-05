package com.hcv.entity;

import com.hcv.dto.CodeRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role extends BaseEntity {

    @Column(name = "name", unique = true, nullable = false, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String name;
    @Column(name = "code", unique = true, nullable = false, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    @Enumerated(EnumType.STRING)
    CodeRole code;

    @ManyToMany(mappedBy = "roles")
    List<User> users = new ArrayList<>();

}

