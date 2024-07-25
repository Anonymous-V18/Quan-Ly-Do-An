package com.hcv.repository;

import com.hcv.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGroupRepository extends JpaRepository<GroupEntity, String> {

    GroupEntity findOneById(String id);
}
