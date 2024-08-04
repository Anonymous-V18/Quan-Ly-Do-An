package com.hcv.converter;

import com.hcv.dto.request.PointInsertInput;
import com.hcv.dto.response.PointDTO;
import com.hcv.dto.response.PointResponse;
import com.hcv.entity.PointEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IPointMapper {

    PointResponse toDTO(PointEntity pointEntity);

    @Mapping(target = "teacherId", ignore = true)
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "id", ignore = true)
    PointEntity toEntity(PointInsertInput pointInsertInput);

    PointEntity toEntity(PointDTO pointDTO);

}
