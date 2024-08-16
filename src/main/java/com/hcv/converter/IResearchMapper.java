package com.hcv.converter;

import com.hcv.dto.request.ResearchInput;
import com.hcv.dto.request.ResearchUpdateInput;
import com.hcv.dto.response.ResearchDTO;
import com.hcv.dto.response.ResearchResponse;
import com.hcv.dto.response.ResearchShowToRegistrationResponse;
import com.hcv.entity.ResearchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface IResearchMapper {

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    ResearchEntity toEntity(ResearchInput researchInput);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    @Mapping(target = "instructorsIds", ignore = true)
    @Mapping(target = "thesisAdvisorId", ignore = true)
    ResearchEntity toEntity(@MappingTarget ResearchEntity oldResearchEntity, ResearchUpdateInput researchUpdateInput);

    @Mapping(target = "isApproved", ignore = true)
    ResearchResponse toShowDTO(ResearchEntity researchEntity);

    @Mapping(target = "feedbacks", ignore = true)
    ResearchResponse toShowDTOAfterApproved(ResearchEntity researchEntity);

    ResearchShowToRegistrationResponse toShowToRegistrationDTO(ResearchEntity researchEntity);

    ResearchDTO toDTO(ResearchEntity researchEntity);

}
