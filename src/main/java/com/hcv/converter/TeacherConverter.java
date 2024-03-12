package com.hcv.converter;

import com.hcv.api_controller.input.TeacherInput;
import com.hcv.dto.TeacherDTO;
import com.hcv.entity.TeacherEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeacherConverter {

    @Autowired
    private ModelMapper mapper;

    public TeacherDTO toDTO(TeacherEntity teacherEntity) {
        return mapper.map(teacherEntity, TeacherDTO.class);
    }

    public TeacherEntity toEntity(TeacherDTO teacherDTO) {
        return mapper.map(teacherDTO, TeacherEntity.class);
    }

    public TeacherDTO toDTO(TeacherInput teacherInput) {
        return mapper.map(teacherInput, TeacherDTO.class);
    }

    public TeacherEntity toEntity(TeacherInput teacherInput) {
        return mapper.map(teacherInput, TeacherEntity.class);
    }


}
