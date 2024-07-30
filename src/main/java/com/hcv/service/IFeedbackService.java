package com.hcv.service;

import com.hcv.dto.FeedbackDTO;
import com.hcv.dto.request.feedback.FeedbackForResearchInput;

public interface IFeedbackService {

    FeedbackDTO insert(FeedbackForResearchInput feedbackForResearchInput);

    FeedbackDTO update(String oldFeedbackId, FeedbackForResearchInput newFeedbackDTOForResearchInput);

    void delete(String[] ids);

    FeedbackDTO findOneById(String id);

}
