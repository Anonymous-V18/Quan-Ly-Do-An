package com.hcv.converter;

import com.hcv.dto.DepartmentDTO;
import com.hcv.entity.DepartmentEntity;
import org.mapstruct.Mapper;

@Mapper
public interface IDepartmentMapper {

    DepartmentDTO toDTO(DepartmentEntity departmentEntity);

    DepartmentEntity toEntity(DepartmentDTO departmentDTO);

}
