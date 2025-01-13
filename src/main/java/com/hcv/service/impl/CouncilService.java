package com.hcv.service.impl;

import com.hcv.converter.ICouncilMapper;
import com.hcv.dto.request.CouncilInput;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.CouncilResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.entity.Council;
import com.hcv.entity.Subject;
import com.hcv.entity.Teacher;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.ICouncilRepository;
import com.hcv.repository.ISubjectRepository;
import com.hcv.repository.ITeacherRepository;
import com.hcv.service.ICouncilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CouncilService implements ICouncilService {

    ICouncilRepository councilRepository;
    ICouncilMapper councilMapper;
    ITeacherRepository teacherRepository;
    ISubjectRepository subjectRepository;

    @Transactional
    @Override
    public void insert(CouncilInput councilInput) {
        boolean isExistBySubjectAndActivated =
                councilRepository.existsBySubject_IdAndIsActivated(councilInput.getSubjectId(), true);
        if (isExistBySubjectAndActivated) {
            throw new AppException(ErrorCode.COUNCIL_EXISTED);
        }

        Set<Teacher> teachers = new HashSet<>(teacherRepository.findAllById(councilInput.getTeacherIds()));
        if (teachers.size() != councilInput.getTeacherIds().size()) {
            throw new AppException(ErrorCode.TEACHER_NOT_EXISTED);
        }
        boolean isTeacherExistedOtherCouncil = teachers.stream().anyMatch(teacher -> teacher.getCouncil() != null);
        if (isTeacherExistedOtherCouncil) {
            throw new AppException(ErrorCode.TEACHER_EXISTED_OTHER_COUNCIL);
        }

        Subject subject = subjectRepository.findById(councilInput.getSubjectId())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));

        Council council = new Council();
        council.setTeachers(teachers);
        council.setSubject(subject);
        council.setIsActivated(true);
        council = councilRepository.save(council);

        Council finalCouncil = council;
        teachers.forEach(teacher -> teacher.setCouncil(finalCouncil));
        teacherRepository.saveAll(teachers);
    }

    @Override
    public void update(String oldCouncilId, CouncilInput councilInput) {
        Council council = councilRepository.findById(oldCouncilId)
                .orElseThrow(() -> new AppException(ErrorCode.COUNCIL_NOT_EXISTED));

        Set<Teacher> teachers = new HashSet<>(teacherRepository.findAllById(councilInput.getTeacherIds()));
        if (teachers.size() != councilInput.getTeacherIds().size()) {
            throw new AppException(ErrorCode.TEACHER_NOT_EXISTED);
        }
        council.setTeachers(teachers);

        if (!councilInput.getSubjectId().equals(council.getSubject().getId())) {
            Subject subject = subjectRepository.findById(councilInput.getSubjectId())
                    .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));
            council.setSubject(subject);
        }

        council.setIsActivated(councilInput.getIsActivated());
        council = councilRepository.save(council);

        Council finalCouncil = council;
        teachers.forEach(teacher -> teacher.setCouncil(finalCouncil));
        teacherRepository.saveAll(teachers);
    }

    @Override
    public void delete(String... ids) {
        // TODO: Dangerous
    }

    @Override
    public CouncilResponse getDetail(String id) {
        Council council = councilRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COUNCIL_NOT_EXISTED));
        return councilMapper.toDTO(council);
    }

    @Override
    public ShowAllResponse<CouncilResponse> showAll(ShowAllRequest showAllRequest) {
        Pageable paging = PageRequest.of(showAllRequest.getCurrentPage() - 1, showAllRequest.getLimit());
        Page<Council> councils = councilRepository.findAll(paging);
        List<CouncilResponse> result = councils.getContent().stream().map(councilMapper::toDTO).toList();
        return ShowAllResponse.<CouncilResponse>builder()
                .currentPage(showAllRequest.getCurrentPage())
                .totalElements(1)
                .totalPages(councils.getTotalPages())
                .responses(result)
                .build();
    }
}
