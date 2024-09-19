package com.hcv.repository;

import com.hcv.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGroupRepository extends JpaRepository<Group, String> {

    Page<Group> findByResearches_Teachers_Id(String id, Pageable pageable);

    long countByResearches_Teachers_Id(String id);

}
