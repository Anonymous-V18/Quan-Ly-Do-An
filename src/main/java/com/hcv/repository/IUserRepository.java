package com.hcv.repository;

import com.hcv.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findOneByUsername(String username);

}
