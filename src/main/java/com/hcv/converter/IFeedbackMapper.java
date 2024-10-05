package com.hcv.converter;

import com.hcv.dto.request.FeedbackForResearchInput;
import com.hcv.dto.response.FeedbackDTO;
import com.hcv.entity.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IFeedbackMapper {

    FeedbackDTO toDTO(Feedback feedback);

    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "research", ignore = true)
    @Mapping(target = "id", ignore = true)
    Feedback toEntity(FeedbackForResearchInput newFeedbackDTOForResearch);


}
