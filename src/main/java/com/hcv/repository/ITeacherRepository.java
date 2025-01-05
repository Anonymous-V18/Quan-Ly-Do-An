package com.hcv.repository;

import com.hcv.dto.CodeRole;
import com.hcv.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ITeacherRepository extends JpaRepository<Teacher, String> {

    Optional<Teacher> findByCode(String code);

    boolean existsById(String id);

    Optional<Teacher> findByCodeAndIdNot(String code, String id);

    List<Teacher> findAllBySubject_Id(String subjectId);

    List<Teacher> findAllBySubject_Department_Id(String departmentId);

    List<Teacher> findAllByUser_Roles_CodeAndDepartment_Id(CodeRole roleCode, String departmentId);

}
