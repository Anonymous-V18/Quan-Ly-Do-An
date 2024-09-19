package com.hcv.service;

import com.hcv.dto.request.FeedbackForResearchInput;
import com.hcv.dto.response.FeedbackDTO;

public interface IFeedbackService {

    FeedbackDTO insert(FeedbackForResearchInput feedbackForResearchInput);

    FeedbackDTO update(String oldFeedbackId, FeedbackForResearchInput newFeedbackDTOForResearchInput);

    void delete(String[] ids);
    
    FeedbackDTO findOneById(String id);

}
