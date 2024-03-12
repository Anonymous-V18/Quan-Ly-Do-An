package com.hcv.repository;

import com.hcv.entity.DepartmentEntity;
import com.hcv.entity.TeacherEntity;
import com.hcv.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITeacherRepository extends JpaRepository<TeacherEntity, Long> {
    TeacherEntity findOneByMaSo(String maSo);

    List<TeacherEntity> findAllByChucVuAndDepartments(String chucVu, DepartmentEntity department);

    TeacherEntity findOneByUsers(UserEntity user);
}
