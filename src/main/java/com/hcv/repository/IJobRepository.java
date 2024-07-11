package com.hcv.repository;

import com.hcv.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJobRepository extends JpaRepository<JobEntity, String> {

}
