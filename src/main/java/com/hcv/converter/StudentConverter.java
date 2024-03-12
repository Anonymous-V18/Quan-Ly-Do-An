package com.hcv.converter;

import com.hcv.api_controller.input.StudentInput;
import com.hcv.dto.StudentDTO;
import com.hcv.entity.StudentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentConverter {

    @Autowired
    private ModelMapper mapper;

    public StudentEntity toEntity(StudentDTO studentDTO) {
        return mapper.map(studentDTO, StudentEntity.class);
    }

    public StudentDTO toDTO(StudentEntity studentEntity) {
        return mapper.map(studentEntity, StudentDTO.class);
    }

    public StudentDTO toDTO(StudentInput studentInput) {
        return mapper.map(studentInput, StudentDTO.class);
    }

    public StudentEntity toEntity(StudentInput studentInput) {
        return mapper.map(studentInput, StudentEntity.class);
    }
}
