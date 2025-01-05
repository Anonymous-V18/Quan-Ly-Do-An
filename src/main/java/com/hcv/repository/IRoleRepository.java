package com.hcv.repository;

import com.hcv.dto.CodeRole;
import com.hcv.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findOneByCode(CodeRole codeRole);

}
