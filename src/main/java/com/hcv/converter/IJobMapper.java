package com.hcv.converter;

import com.hcv.dto.request.JobInput;
import com.hcv.dto.response.JobDTO;
import com.hcv.entity.JobEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IJobMapper {

    JobDTO toDTO(JobEntity jobEntity);

    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "groups", ignore = true)
    JobEntity toEntity(JobInput jobInput);

}
