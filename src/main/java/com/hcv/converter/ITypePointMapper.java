package com.hcv.converter;

import com.hcv.dto.response.TypePointResponse;
import com.hcv.entity.TypePoint;
import org.mapstruct.Mapper;

@Mapper
public interface ITypePointMapper {

    TypePointResponse toDTO(TypePoint typePoint);
    
}
