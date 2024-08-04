package com.hcv.converter;

import com.hcv.dto.response.GroupDTO;
import com.hcv.dto.response.GroupResponse;
import com.hcv.entity.GroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IGroupMapper {

    @Mapping(target = "jobs", ignore = true)
    GroupEntity toEntity(GroupDTO groupDTO);

    GroupDTO toDTO(GroupEntity groupEntity);

    GroupResponse toShowDTO(GroupEntity groupEntity);

}
