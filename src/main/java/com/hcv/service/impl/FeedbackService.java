package com.hcv.service.impl;

import com.hcv.converter.IFeedbackMapper;
import com.hcv.dto.FeedbackDTO;
import com.hcv.dto.request.feedback.FeedbackForResearchInput;
import com.hcv.entity.FeedbackEntity;
import com.hcv.entity.ResearchEntity;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IFeedbackRepository;
import com.hcv.repository.IResearchRepository;
import com.hcv.repository.ITeacherRepository;
import com.hcv.service.IFeedbackService;
import com.hcv.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FeedbackService implements IFeedbackService {

    ITeacherRepository teacherRepository;
    IResearchRepository researchRepository;
    IFeedbackRepository feedbackRepository;
    IFeedbackMapper feedbackMapper;
    IUserService userService;

    @Override
    public FeedbackDTO insert(FeedbackForResearchInput feedbackForResearchInput) {
        FeedbackEntity feedbackEntity = feedbackMapper.toEntity(feedbackForResearchInput);

        String researchId = feedbackForResearchInput.getResearchID();
        ResearchEntity researchEntity = researchRepository.findOneById(researchId);
        if (researchEntity == null) {
            throw new AppException(ErrorCode.RESEARCH_NOT_EXISTED);
        }
        feedbackEntity.setResearches(researchEntity);

        feedbackRepository.save(feedbackEntity);

        return feedbackMapper.toDTO(feedbackEntity);
    }

    @Override
    public FeedbackDTO update(String idOldFeedbackDTO, FeedbackForResearchInput newFeedbackDTOForResearchInput) {
        FeedbackEntity feedbackEntity = feedbackRepository.findOneById(idOldFeedbackDTO);
        if (feedbackEntity == null) {
            throw new AppException(ErrorCode.FEEDBACK_NOT_EXISTED);
        }

        String authorFeedbackId = feedbackEntity.getSendFrom();
        String currentUserId = userService.getSubToken();
        if (!authorFeedbackId.equals(currentUserId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        String receiverId = newFeedbackDTOForResearchInput.getSendTo();
        boolean isReceiverValid = teacherRepository.existsById(receiverId);
        if (isReceiverValid) {
            throw new AppException(ErrorCode.TEACHER_NOT_EXISTED);
        }
        feedbackEntity.setSendTo(newFeedbackDTOForResearchInput.getSendTo());

        String researchID = newFeedbackDTOForResearchInput.getResearchID();
        ResearchEntity researchEntity = researchRepository.findOneById(researchID);
        if (researchEntity == null) {
            throw new AppException(ErrorCode.RESEARCH_NOT_EXISTED);
        }
        
        feedbackRepository.save(feedbackEntity);

        return feedbackMapper.toDTO(feedbackEntity);
    }

    @Override
    public void delete(String[] ids) {
        feedbackRepository.deleteAllById(Arrays.asList(ids));
    }

    @Override
    public FeedbackDTO findOneById(String id) {
        FeedbackEntity feedbackEntity = feedbackRepository.findOneById(id);
        return feedbackEntity == null ? null : feedbackMapper.toDTO(feedbackEntity);
    }
}
