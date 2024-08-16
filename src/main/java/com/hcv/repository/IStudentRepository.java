package com.hcv.repository;

import com.hcv.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IStudentRepository extends JpaRepository<StudentEntity, String> {

    Optional<StudentEntity> findByCode(String code);

    boolean existsByCode(String code);

    List<StudentEntity> findByGroups_IdIn(List<String> ids);

    List<StudentEntity> findByUsers_IsGraduate(Integer isGraduate);

}
