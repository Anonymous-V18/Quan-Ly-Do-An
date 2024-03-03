package com.hcv.converter;

import com.hcv.dto.DepartmentDTO;
import com.hcv.entity.DepartmentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepartmentConverter {

    @Autowired
    private ModelMapper mapper;

    public DepartmentDTO toDTO(DepartmentEntity departmentEntity) {
        return mapper.map(departmentEntity, DepartmentDTO.class);
    }

    public DepartmentEntity toEntity(DepartmentDTO departmentDTO) {
        return mapper.map(departmentDTO, DepartmentEntity.class);
    }

}
