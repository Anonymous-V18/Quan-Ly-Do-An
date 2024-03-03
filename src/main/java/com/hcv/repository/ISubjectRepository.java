package com.hcv.repository;

import com.hcv.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISubjectRepository extends JpaRepository<SubjectEntity, Long> {
    SubjectEntity findOneByName(String name);

    SubjectEntity findOneById(Long id);
}
