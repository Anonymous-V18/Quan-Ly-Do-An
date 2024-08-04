package com.hcv.converter;

import com.hcv.dto.request.FeedbackForResearchInput;
import com.hcv.dto.response.FeedbackDTO;
import com.hcv.entity.FeedbackEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IFeedbackMapper {

    FeedbackDTO toDTO(FeedbackEntity feedbackEntity);

    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "researches", ignore = true)
    @Mapping(target = "id", ignore = true)
    FeedbackEntity toEntity(FeedbackForResearchInput newFeedbackDTOForResearch);


}
