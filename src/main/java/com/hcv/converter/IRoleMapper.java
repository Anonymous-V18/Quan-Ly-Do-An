package com.hcv.converter;

import com.hcv.dto.response.RoleDTO;
import com.hcv.entity.Role;
import org.mapstruct.Mapper;

@Mapper
public interface IRoleMapper {

    RoleDTO toDTO(Role role);

}
