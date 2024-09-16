package com.hcv.repository;

import com.hcv.dto.StatusResearch;
import com.hcv.entity.Research;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface IResearchRepository extends JpaRepository<Research, String> {

    Page<Research> findByStatusInAndSubjects_Id(Collection<StatusResearch> statuses, String id, Pageable pageable);

}
