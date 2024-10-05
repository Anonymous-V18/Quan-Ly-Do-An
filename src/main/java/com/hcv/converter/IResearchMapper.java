package com.hcv.converter;

import com.hcv.dto.request.ResearchInput;
import com.hcv.dto.request.ResearchUpdateInput;
import com.hcv.dto.response.ResearchDTO;
import com.hcv.dto.response.ResearchResponse;
import com.hcv.dto.response.ResearchShowToRegistrationResponse;
import com.hcv.entity.Research;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface IResearchMapper {

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    Research toEntity(ResearchInput researchInput);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    @Mapping(target = "instructorsIds", ignore = true)
    @Mapping(target = "thesisAdvisorId", ignore = true)
    Research toEntity(@MappingTarget Research oldResearch, ResearchUpdateInput researchUpdateInput);

    @Mapping(target = "isApproved", ignore = true)
    ResearchResponse toShowDTO(Research research);

    @Mapping(target = "feedbacks", ignore = true)
    ResearchResponse toShowDTOAfterApproved(Research research);

    ResearchShowToRegistrationResponse toShowToRegistrationDTO(Research research);

    ResearchDTO toDTO(Research research);

}
