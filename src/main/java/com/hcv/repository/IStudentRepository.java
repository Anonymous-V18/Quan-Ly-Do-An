package com.hcv.repository;

import com.hcv.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStudentRepository extends JpaRepository<StudentEntity, Long> {
    StudentEntity findOneByMaSo(String maSo);

    StudentEntity findOneById(Long id);
}
