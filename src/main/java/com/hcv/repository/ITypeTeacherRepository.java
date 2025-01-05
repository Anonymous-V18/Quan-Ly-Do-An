package com.hcv.repository;

import com.hcv.entity.TypeTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITypeTeacherRepository extends JpaRepository<TypeTeacher, String> {

    Optional<TypeTeacher> findByCode(String code);
    
}
