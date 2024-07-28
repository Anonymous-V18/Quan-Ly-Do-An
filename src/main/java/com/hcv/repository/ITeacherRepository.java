package com.hcv.repository;

import com.hcv.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITeacherRepository extends JpaRepository<TeacherEntity, String> {
    TeacherEntity findOneByMaSo(String maSo);

    TeacherEntity findOneById(String id);

    boolean existsByMaSo(String maSo);

    boolean existsByUsers_Roles_Name(String name);


}
