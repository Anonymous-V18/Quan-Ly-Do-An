package com.hcv.repository;

import com.hcv.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface IRoleRepository extends JpaRepository<Role, String> {

    List<Role> findByNameIn(Collection<String> names);

}
