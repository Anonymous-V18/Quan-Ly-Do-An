package com.hcv.repository;

import com.hcv.entity.InvalidatedTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInvalidatedTokenRepository extends JpaRepository<InvalidatedTokenEntity, String> {
}
