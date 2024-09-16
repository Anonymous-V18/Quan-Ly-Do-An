package com.hcv.converter;

import com.hcv.dto.response.DepartmentDTO;
import com.hcv.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IDepartmentMapper {

    DepartmentDTO toDTO(Department department);

    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "students", ignore = true)
    Department toEntity(DepartmentDTO departmentDTO);

}
