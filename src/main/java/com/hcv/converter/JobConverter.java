package com.hcv.converter;

import com.hcv.api_controller.input.JobForTeacherInput;
import com.hcv.dto.JobDTO;
import com.hcv.entity.JobEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobConverter {

    @Autowired
    private ModelMapper mapper;

    public JobDTO toDTO(JobEntity jobEntity) {
        return mapper.map(jobEntity, JobDTO.class);
    }

    public JobEntity toEntity(JobDTO jobDTO) {
        return mapper.map(jobDTO, JobEntity.class);
    }

    public JobEntity toEntity(JobForTeacherInput jobForTeacherInput) {
        return mapper.map(jobForTeacherInput, JobEntity.class);
    }

    public JobDTO toDTO(JobForTeacherInput jobForTeacherInput) {
        return mapper.map(jobForTeacherInput, JobDTO.class);
    }

}
