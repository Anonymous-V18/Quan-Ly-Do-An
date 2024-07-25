package com.hcv.converter;

import com.hcv.dto.FeedbackDTO;
import com.hcv.dto.request.FeedbackForResearchInput;
import com.hcv.entity.FeedbackEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface IFeedbackMapper {


    FeedbackDTO toDTO(FeedbackEntity feedbackEntity);

    FeedbackDTO toDTO(FeedbackForResearchInput newFeedbackDTOForResearch);

    FeedbackDTO toDTO(@MappingTarget FeedbackDTO oldFeedbackDTO, FeedbackForResearchInput newFeedbackDTOForResearch);

    FeedbackEntity toEntity(FeedbackDTO feedbackDTO);

}
