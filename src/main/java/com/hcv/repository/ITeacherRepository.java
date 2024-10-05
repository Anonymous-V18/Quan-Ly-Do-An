package com.hcv.repository;

import com.hcv.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITeacherRepository extends JpaRepository<Teacher, String> {

    Optional<Teacher> findByCode(String code);

    boolean existsById(String id);

    Optional<Teacher> findByCodeAndIdNot(String code, String id);
}
