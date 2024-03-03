package com.hcv.converter;

import com.hcv.dto.SubjectDTO;
import com.hcv.entity.SubjectEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectConverter {

    @Autowired
    private ModelMapper mapper;

    public SubjectDTO toDTO(SubjectEntity subjectEntity) {
        return mapper.map(subjectEntity, SubjectDTO.class);
    }

    public SubjectEntity toEntity(SubjectDTO subjectDTO) {
        return mapper.map(subjectDTO, SubjectEntity.class);
    }
}
