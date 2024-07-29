package com.hcv.converter;

import com.hcv.dto.GroupDTO;
import com.hcv.dto.response.GroupResponse;
import com.hcv.entity.GroupEntity;
import org.mapstruct.Mapper;

@Mapper
public interface IGroupMapper {

    GroupEntity toEntity(GroupDTO groupDTO);

    GroupDTO toDTO(GroupEntity groupEntity);

    GroupResponse toShowDTO(GroupEntity groupEntity);

}
