package com.hcv.converter;

import com.hcv.dto.request.ResearchInput;
import com.hcv.dto.response.ResearchDTO;
import com.hcv.dto.response.ResearchResponse;
import com.hcv.entity.ResearchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IResearchMapper {

    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    ResearchEntity toEntity(ResearchInput researchInput);

    ResearchResponse toShowDTO(ResearchEntity researchEntity);

    ResearchDTO toDTO(ResearchEntity researchEntity);

}
