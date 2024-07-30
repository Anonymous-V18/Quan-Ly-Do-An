package com.hcv.converter;

import com.hcv.dto.FeedbackDTO;
import com.hcv.dto.request.feedback.FeedbackForResearchInput;
import com.hcv.entity.FeedbackEntity;
import org.mapstruct.Mapper;

@Mapper
public interface IFeedbackMapper {


    FeedbackDTO toDTO(FeedbackEntity feedbackEntity);

    FeedbackEntity toEntity(FeedbackForResearchInput newFeedbackDTOForResearch);

    FeedbackEntity toEntity(FeedbackDTO feedbackDTO);

}
