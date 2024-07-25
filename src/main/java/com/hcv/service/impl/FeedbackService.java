package com.hcv.service.impl;

import com.hcv.converter.IFeedbackMapper;
import com.hcv.dto.FeedbackDTO;
import com.hcv.dto.ResearchDTO;
import com.hcv.dto.TeacherDTO;
import com.hcv.dto.request.FeedbackForResearchInput;
import com.hcv.entity.FeedbackEntity;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IFeedbackRepository;
import com.hcv.service.IFeedbackService;
import com.hcv.service.IResearchService;
import com.hcv.service.ITeacherService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FeedbackService implements IFeedbackService {

    ITeacherService teacherService;
    IResearchService researchService;
    IFeedbackRepository feedbackRepository;
    IFeedbackMapper feedbackMapper;

    @Override
    public FeedbackDTO insert(FeedbackForResearchInput feedbackForResearchInput) {
        FeedbackDTO feedbackDTO = feedbackMapper.toDTO(feedbackForResearchInput);

        TeacherDTO sendTo = teacherService.findOneById(feedbackDTO.getSendTo());
        TeacherDTO sendFrom = teacherService.findOneById(feedbackDTO.getSendFrom());
        if (sendTo == null || sendFrom == null) {
            throw new AppException(ErrorCode.TEACHER_NOT_EXISTED);
        }

        String researchId = feedbackForResearchInput.getResearchID();
        ResearchDTO researchDTO = researchService.findOneById(researchId);
        if (researchDTO == null) {
            throw new AppException(ErrorCode.RESEARCH_NOT_EXISTED);
        }
        feedbackDTO.setResearches(researchDTO);

        FeedbackEntity feedbackEntity = feedbackMapper.toEntity(feedbackDTO);
        feedbackRepository.save(feedbackEntity);

        return feedbackMapper.toDTO(feedbackEntity);
    }

    @Override
    public FeedbackDTO update(FeedbackDTO oldFeedbackDTO, FeedbackForResearchInput newFeedbackDTOForResearchInput) {
        oldFeedbackDTO = feedbackMapper.toDTO(oldFeedbackDTO, newFeedbackDTOForResearchInput);

        TeacherDTO sendTo = teacherService.findOneById(newFeedbackDTOForResearchInput.getSendTo());
        TeacherDTO sendFrom = teacherService.findOneById(newFeedbackDTOForResearchInput.getSendFrom());
        if (sendTo == null || sendFrom == null) {
            throw new AppException(ErrorCode.TEACHER_NOT_EXISTED);
        }

        String researchID = newFeedbackDTOForResearchInput.getResearchID();
        ResearchDTO researchDTO = researchService.findOneById(researchID);
        if (researchDTO == null) {
            throw new AppException(ErrorCode.RESEARCH_NOT_EXISTED);
        }
        oldFeedbackDTO.setResearches(researchDTO);

        FeedbackEntity feedbackEntity = feedbackMapper.toEntity(oldFeedbackDTO);
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
