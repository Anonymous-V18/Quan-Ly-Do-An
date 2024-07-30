package com.hcv.converter;

import com.hcv.dto.PointDTO;
import com.hcv.dto.request.point.PointInsertInput;
import com.hcv.dto.response.PointResponse;
import com.hcv.entity.PointEntity;
import org.mapstruct.Mapper;

@Mapper
public interface IPointMapper {

    PointResponse toDTO(PointEntity pointEntity);

    PointEntity toEntity(PointInsertInput pointInsertInput);

    PointEntity toEntity(PointDTO pointDTO);

}
