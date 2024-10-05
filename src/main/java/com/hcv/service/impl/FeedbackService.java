package com.hcv.service.impl;

import com.hcv.converter.IFeedbackMapper;
import com.hcv.dto.request.FeedbackForResearchInput;
import com.hcv.dto.response.FeedbackDTO;
import com.hcv.entity.Feedback;
import com.hcv.entity.Research;
import com.hcv.entity.Teacher;
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

        String currentUserId = userService.getClaimsToken().get("sub").toString();
        Teacher teacher = teacherRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED));

        Feedback feedback = feedbackMapper.toEntity(feedbackForResearchInput);

        String researchId = feedbackForResearchInput.getResearchID();
        Research research = researchRepository.findById(researchId)
                .orElseThrow(() -> new AppException(ErrorCode.RESEARCH_NOT_EXISTED));

        feedback.setSendFromName(teacher.getName());
        feedback.setSendFrom(teacher.getCode());
        feedback.setResearch(research);

        feedback = feedbackRepository.save(feedback);

        return feedbackMapper.toDTO(feedback);
    }


    @Override
    public FeedbackDTO update(String oldFeedbackId, FeedbackForResearchInput newFeedbackDTOForResearchInput) {
        Feedback feedback = feedbackRepository.findById(oldFeedbackId)
                .orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOT_EXISTED));

        String authorFeedbackId = feedback.getCreatedBy();
        String currentUserId = userService.getClaimsToken().get("sub").toString();
        if (!authorFeedbackId.equals(currentUserId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        feedback.setMessage(newFeedbackDTOForResearchInput.getMessage());

        String researchID = newFeedbackDTOForResearchInput.getResearchID();
        Research research = researchRepository.findById(researchID)
                .orElseThrow(() -> new AppException(ErrorCode.RESEARCH_NOT_EXISTED));
        feedback.setResearch(research);
        feedbackRepository.save(feedback);

        return feedbackMapper.toDTO(feedback);
    }

    @Override
    public void delete(String[] ids) {
        feedbackRepository.deleteAllById(Arrays.asList(ids));
    }

    @Override
    public FeedbackDTO findOneById(String id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOT_EXISTED));
        return feedbackMapper.toDTO(feedback);
    }


}
