package com.hcv.repository;

import com.hcv.entity.SystemVariable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISystemVariableRepository extends JpaRepository<SystemVariable, String> {

    Optional<SystemVariable> findByCode(String code);

}