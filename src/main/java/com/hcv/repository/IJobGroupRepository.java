package com.hcv.repository;

import com.hcv.entity.JobGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJobGroupRepository extends JpaRepository<JobGroup, String> {

    long countByGroup_Id(String id);

}
