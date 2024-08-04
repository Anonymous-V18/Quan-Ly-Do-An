package com.hcv.service.impl;

import com.hcv.converter.IResearchMapper;
import com.hcv.dto.request.ResearchCancelRegistrationInput;
import com.hcv.dto.request.ResearchInput;
import com.hcv.dto.request.ResearchRegisterInput;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.ResearchDTO;
import com.hcv.dto.response.ResearchResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.entity.ResearchEntity;
import com.hcv.entity.StudentEntity;
import com.hcv.entity.SubjectEntity;
import com.hcv.entity.TeacherEntity;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IResearchRepository;
import com.hcv.repository.IStudentRepository;
import com.hcv.repository.ISubjectRepository;
import com.hcv.repository.ITeacherRepository;
import com.hcv.service.IResearchService;
import com.hcv.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResearchService implements IResearchService {

    ITeacherRepository teacherRepository;
    ISubjectRepository subjectRepository;
    IResearchRepository researchRepository;
    IResearchMapper mapper;
    IStudentRepository studentRepository;
    IUserService userService;

    @Override
    public ResearchDTO insert(ResearchInput researchInput) {
        if (researchInput.getMaDeTai().isBlank()) {
            researchInput.setMaDeTai(UUID.randomUUID().toString());
        }

        ResearchEntity researchEntity = mapper.toEntity(researchInput);

        List<String> teachersID = List.of(researchInput.getGvhd(), researchInput.getGvpb());
        if (teachersID.getFirst().compareTo(teachersID.getLast()) == 0) {
            throw new AppException(ErrorCode.TEACHER_DUPLICATED);
        }
        List<TeacherEntity> teachers = teachersID.stream()
                .map(teacherRepository::findOneById)
                .toList();
        if (teachers.contains(null)) {
            throw new AppException(ErrorCode.TEACHER_NOT_EXISTED);
        }
        researchEntity.setTeachers(teachers);

        List<SubjectEntity> subjects = researchInput.getSubjectsID().stream()
                .map(subjectRepository::findOneById)
                .toList();
        if (subjects.contains(null)) {
            throw new AppException(ErrorCode.SUBJECT_NOT_EXISTED);
        }
        researchEntity.setSubjects(subjects);

        researchEntity = researchRepository.save(researchEntity);

        return mapper.toDTO(researchEntity);
    }

    @Override
    public ResearchDTO update(String oldResearchId, ResearchInput newResearchDTO) {
        ResearchEntity researchEntity = researchRepository.findOneById(oldResearchId);

        List<String> teachersID = List.of(newResearchDTO.getGvhd(), newResearchDTO.getGvpb());
        if (teachersID.getFirst().compareTo(teachersID.getLast()) == 0) {
            throw new AppException(ErrorCode.TEACHER_DUPLICATED);
        }
        List<TeacherEntity> teachers = teachersID.stream()
                .map(teacherRepository::findOneById)
                .toList();
        if (teachers.contains(null)) {
            throw new AppException(ErrorCode.TEACHER_NOT_EXISTED);
        }
        researchEntity.setTeachers(teachers);

        List<SubjectEntity> subjects = newResearchDTO.getSubjectsID().stream()
                .map(subjectRepository::findOneById)
                .toList();
        if (subjects.contains(null)) {
            throw new AppException(ErrorCode.SUBJECT_NOT_EXISTED);
        }
        researchEntity.setSubjects(subjects);

        researchEntity = researchRepository.save(researchEntity);

        return mapper.toDTO(researchEntity);
    }

    @Override
    public void delete(String[] ids) {
        researchRepository.deleteAllById(Arrays.stream(ids).toList());
    }

    @Override
    public ResearchDTO findOneById(String id) {
        ResearchEntity researchEntity = researchRepository.findOneById(id);
        return researchEntity == null ? null : mapper.toDTO(researchEntity);
    }

    @Override
    public int countAll() {
        return (int) researchRepository.count();
    }

    @Override
    public ShowAllResponse<ResearchResponse> showAll(ShowAllRequest showAllRequest) {
        int page = showAllRequest.getPage();
        int limit = showAllRequest.getLimit();
        int totalPages = (int) Math.ceil((1.0 * countAll()) / limit);

        Pageable paging = PageRequest.of(
                page - 1,
                limit,
                Sort.by(Sort.Direction.fromString(showAllRequest.getOrderDirection()), showAllRequest.getOrderBy())
        );
        Page<ResearchEntity> researchEntityList = researchRepository.findAll(paging);
        List<ResearchEntity> resultEntity = researchEntityList.getContent();
        List<ResearchResponse> resultDTO = resultEntity.stream().map(mapper::toShowDTO).toList();

        return ShowAllResponse.<ResearchResponse>builder()
                .page(page)
                .totalPages(totalPages)
                .responses(resultDTO)
                .build();
    }

    @Override
    public void registerResearch(ResearchRegisterInput researchRegisterInput) {
        researchRegisterInput.setStudentID(userService.getSubToken());

        String researchID = researchRegisterInput.getResearchID();
        ResearchEntity researchEntity = researchRepository.findOneById(researchID);
        if (researchEntity == null) {
            throw new AppException(ErrorCode.RESEARCH_NOT_EXISTED);
        }

        String studentID = researchRegisterInput.getStudentID();
        StudentEntity studentEntity = studentRepository.findOneById(studentID);
        if (studentEntity == null) {
            throw new AppException(ErrorCode.STUDENT_NOT_EXIST);
        }
        researchEntity.setGroups(studentEntity.getGroups());
        researchRepository.save(researchEntity);
    }

    @Override
    public void cancelRegistrationResearch(ResearchCancelRegistrationInput researchCancelRegistrationInput) {
        String researchID = researchCancelRegistrationInput.getResearchID();
        ResearchEntity researchEntity = researchRepository.findOneById(researchID);
        if (researchEntity == null) {
            throw new AppException(ErrorCode.RESEARCH_NOT_EXISTED);
        }
        researchEntity.setGroups(null);
        researchRepository.save(researchEntity);
    }
}
