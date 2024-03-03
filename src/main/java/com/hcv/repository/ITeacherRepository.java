package com.hcv.repository;

import com.hcv.entity.SubjectEntity;
import com.hcv.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITeacherRepository extends JpaRepository<TeacherEntity, Long> {
    TeacherEntity findOneByMaSo(String maSo);

    TeacherEntity findOneByChucVuAndSubjects(String chucVu, SubjectEntity subject);

}
