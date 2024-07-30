package com.hcv.converter;

import com.hcv.dto.ResearchDTO;
import com.hcv.dto.request.research.ResearchInput;
import com.hcv.dto.response.ResearchResponse;
import com.hcv.entity.ResearchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface IResearchMapper {


    ResearchEntity toEntity(ResearchDTO researchDTO);

    ResearchResponse toShowDTO(ResearchEntity researchEntity);

    ResearchDTO toDTO(ResearchEntity researchEntity);

    ResearchDTO toDTO(ResearchInput researchInput);

    ResearchDTO toDTO(@MappingTarget ResearchDTO oldResearchDTO, ResearchInput newResearchDTO);

}
