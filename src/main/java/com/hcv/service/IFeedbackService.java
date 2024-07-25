package com.hcv.service;

import com.hcv.dto.FeedbackDTO;
import com.hcv.dto.request.FeedbackForResearchInput;

public interface IFeedbackService {

    FeedbackDTO insert(FeedbackForResearchInput feedbackForResearchInput);

    FeedbackDTO update(FeedbackDTO oldFeedbackDTO, FeedbackForResearchInput newFeedbackDTOForResearchInput);

    void delete(String[] ids);

    FeedbackDTO findOneById(String id);

}
