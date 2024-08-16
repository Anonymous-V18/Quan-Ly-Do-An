package com.hcv.repository;

import com.hcv.entity.ResearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IResearchRepository extends JpaRepository<ResearchEntity, String> {

}
