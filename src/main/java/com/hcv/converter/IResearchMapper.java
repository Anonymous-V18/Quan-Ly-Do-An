package com.hcv.converter;

import com.hcv.dto.ResearchDTO;
import com.hcv.dto.request.research.ResearchInput;
import com.hcv.dto.response.ResearchResponse;
import com.hcv.entity.ResearchEntity;
import org.mapstruct.Mapper;

@Mapper
public interface IResearchMapper {

    ResearchEntity toEntity(ResearchInput researchInput);

    ResearchResponse toShowDTO(ResearchEntity researchEntity);

    ResearchDTO toDTO(ResearchEntity researchEntity);

}
