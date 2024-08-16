package com.hcv.repository;

import com.hcv.entity.GroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGroupRepository extends JpaRepository<GroupEntity, String> {

    Page<GroupEntity> findByResearches_Teachers_Id(String id, Pageable pageable);

}
