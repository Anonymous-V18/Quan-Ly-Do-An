package com.hcv.service.impl;

import com.hcv.converter.IFeedbackMapper;
import com.hcv.dto.request.FeedbackForResearchInput;
import com.hcv.dto.response.FeedbackDTO;
import com.hcv.entity.FeedbackEntity;
import com.hcv.entity.ResearchEntity;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IFeedbackRepository;
import com.hcv.repository.IResearchRepository;
import com.hcv.repository.IStudentRepository;
import com.hcv.repository.ITeacherRepository;
import com.hcv.service.IFeedbackService;
import com.hcv.service.IGroupService;
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
    IStudentRepository studentRepository;
    IGroupService groupService;

    @Override
    public FeedbackDTO insert(FeedbackForResearchInput feedbackForResearchInput) {

        FeedbackEntity feedbackEntity = feedbackMapper.toEntity(feedbackForResearchInput);

        String researchId = feedbackForResearchInput.getResearchID();
        ResearchEntity researchEntity = researchRepository.findById(researchId)
                .orElseThrow(() -> new AppException(ErrorCode.RESEARCH_NOT_EXISTED));

        feedbackEntity.setSendTo(researchId);
        feedbackEntity.setResearches(researchEntity);

        feedbackEntity = feedbackRepository.save(feedbackEntity);

        return feedbackMapper.toDTO(feedbackEntity);
    }


    @Override
    public FeedbackDTO update(String oldFeedbackId, FeedbackForResearchInput newFeedbackDTOForResearchInput) {
        FeedbackEntity feedbackEntity = feedbackRepository.findById(oldFeedbackId)
                .orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOT_EXISTED));

        String authorFeedbackId = feedbackEntity.getSendFrom();
        String currentUserId = userService.getClaimsToken().get("sub").toString();
        if (!authorFeedbackId.equals(currentUserId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        String researchID = newFeedbackDTOForResearchInput.getResearchID();
        ResearchEntity researchEntity = researchRepository.findById(researchID)
                .orElseThrow(() -> new AppException(ErrorCode.RESEARCH_NOT_EXISTED));
        feedbackEntity.setResearches(researchEntity);
        feedbackEntity.setSendTo(researchID);

        feedbackRepository.save(feedbackEntity);

        return feedbackMapper.toDTO(feedbackEntity);
    }

    @Override
    public void delete(String[] ids) {
        feedbackRepository.deleteAllById(Arrays.asList(ids));
    }

    @Override
    public FeedbackDTO findOneById(String id) {
        FeedbackEntity feedbackEntity = feedbackRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOT_EXISTED));
        return feedbackMapper.toDTO(feedbackEntity);
    }


}
