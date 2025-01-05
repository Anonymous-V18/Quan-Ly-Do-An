package com.hcv.repository;

import com.hcv.entity.Council;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICouncilRepository extends JpaRepository<Council, String> {

    boolean existsBySubject_IdAndIsActivated(String subjectId, Boolean isActivated);

    Optional<Council> findBySubject_id(String subjectId);
}