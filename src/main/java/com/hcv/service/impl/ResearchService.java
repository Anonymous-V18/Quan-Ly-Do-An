package com.hcv.service.impl;

import com.hcv.constant.StatusResearchConst;
import com.hcv.converter.IResearchMapper;
import com.hcv.dto.StatusResearch;
import com.hcv.dto.request.*;
import com.hcv.dto.response.ResearchDTO;
import com.hcv.dto.response.ResearchResponse;
import com.hcv.dto.response.ResearchShowToRegistrationResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.entity.ResearchEntity;
import com.hcv.entity.StudentEntity;
import com.hcv.entity.SubjectEntity;
import com.hcv.entity.TeacherEntity;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IResearchRepository;
import com.hcv.repository.IStudentRepository;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResearchService implements IResearchService {

    ITeacherRepository teacherRepository;
    IResearchRepository researchRepository;
    IResearchMapper mapper;
    IStudentRepository studentRepository;
    IUserService userService;

    @Override
    public void checkDataBeforeInsert(ResearchInsertFromFileInput researchInsertFromFileInput) {
        List<ResearchInput> data = researchInsertFromFileInput.getResearches();

        List<ResearchInput> researchInputList = data.stream()
                .filter(researchInput -> !researchInput.getInstructorsIds().isEmpty())
                .distinct()
                .toList();

        if (!researchInputList.isEmpty()) {
            List<String> teacherIds = new ArrayList<>();
            for (ResearchInput researchInput : researchInputList) {
                teacherIds.addAll(researchInput.getInstructorsIds());
            }

            if (!teacherRepository.existsByIdIn(teacherIds)) {
                throw new AppException(ErrorCode.TEACHER_NOT_EXISTED);
            }
        }

    }

    @Override
    public List<ResearchDTO> insertFromFile(ResearchInsertFromFileInput researchInsertFromFileInput) {
        this.checkDataBeforeInsert(researchInsertFromFileInput);
        return researchInsertFromFileInput.getResearches().stream().map(this::insert).toList();
    }

    @Override
    public ResearchDTO insert(ResearchInput researchInput) {
        researchInput.setCode(this.generateResearchCode(researchInput));

        ResearchEntity researchEntity = mapper.toEntity(researchInput);

        List<String> teacherIds = researchInput.getInstructorsIds();
        String creatorId = userService.getClaimsToken().get("sub").toString();
        teacherIds.add(creatorId);
        List<TeacherEntity> teachers = teacherIds.stream()
                .map(teacherId -> teacherRepository.findById(teacherId)
                        .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED)))
                .toList();

        researchEntity.setInstructorsIds(teacherIds);
        researchEntity.setTeachers(teachers);

        List<SubjectEntity> subjects = teachers.stream()
                .map(TeacherEntity::getSubjects)
                .distinct()
                .toList();

        if (subjects.contains(null)) {
            throw new AppException(ErrorCode.SUBJECT_NOT_EXISTED);
        }

        researchEntity.setSubjects(subjects);

        researchEntity.setStatus(StatusResearch.valueOf(StatusResearchConst.PENDING_APPROVE));

        researchEntity = researchRepository.save(researchEntity);

        return mapper.toDTO(researchEntity);
    }

    @Override
    public ResearchDTO update(String oldResearchId, ResearchUpdateInput newResearchUpdateInput) {
        ResearchEntity researchEntity = researchRepository.findById(oldResearchId)
                .orElseThrow(() -> new AppException(ErrorCode.RESEARCH_NOT_EXISTED));

        researchEntity = mapper.toEntity(researchEntity, newResearchUpdateInput);

        List<String> newTeacherIds = new ArrayList<>();

        List<String> newInstructorsIds = newResearchUpdateInput.getInstructorsIds();
        if (!new HashSet<>(researchEntity.getInstructorsIds()).containsAll(newInstructorsIds)) {
            newTeacherIds.addAll(newInstructorsIds);
        }

        String newThesisAdvisor = newResearchUpdateInput.getThesisAdvisorId();
        if (!newThesisAdvisor.isBlank() && !newThesisAdvisor.equals(researchEntity.getThesisAdvisorId())) {
            newTeacherIds.add(newThesisAdvisor);
        }

        if (!newTeacherIds.isEmpty()) {
            List<TeacherEntity> teachers = newTeacherIds.stream()
                    .map(teacherId -> teacherRepository.findById(teacherId)
                            .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED)))
                    .toList();

            researchEntity.setInstructorsIds(newResearchUpdateInput.getInstructorsIds());
            researchEntity.setThesisAdvisorId(newThesisAdvisor);
            researchEntity.setTeachers(teachers);

            List<SubjectEntity> subjects = teachers.stream()
                    .map(TeacherEntity::getSubjects)
                    .distinct()
                    .toList();
            if (subjects.contains(null)) {
                throw new AppException(ErrorCode.SUBJECT_NOT_EXISTED);
            }
            researchEntity.setSubjects(subjects);
        }

        researchEntity.setCode(this.generateResearchCode(
                ResearchInput.builder()
                        .name(newResearchUpdateInput.getName())
                        .stage(newResearchUpdateInput.getStage())
                        .schoolYear(newResearchUpdateInput.getSchoolYear())
                        .build())
        );

        researchEntity = researchRepository.save(researchEntity);

        return mapper.toDTO(researchEntity);
    }

    @Override
    public ResearchDTO markApproved(String id) {
        ResearchEntity researchEntity = researchRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESEARCH_NOT_EXISTED));

        researchEntity.setStatus(StatusResearch.valueOf(StatusResearchConst.APPROVED));
        researchRepository.save(researchEntity);

        return mapper.toDTO(researchEntity);
    }

    @Override
    public void delete(String[] ids) {
        List<ResearchEntity> researchEntityList = researchRepository.findAllById(Arrays.asList(ids));
        researchEntityList.forEach(researchEntity ->
                researchEntity.setStatus(StatusResearch.valueOf(StatusResearchConst.DELETED)));

        researchRepository.saveAll(researchEntityList);
    }

    @Override
    public ResearchDTO findOneById(String id) {
        ResearchEntity researchEntity = researchRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESEARCH_NOT_EXISTED));
        return mapper.toDTO(researchEntity);
    }

    @Override
    public ResearchResponse showDetail(String id) {
        ResearchEntity researchEntity = researchRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESEARCH_NOT_EXISTED));

        if (researchEntity.getStatus().name().equals("AS")) {
            return mapper.toShowDTOAfterApproved(researchEntity);
        }

        return mapper.toShowDTO(researchEntity);

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
    public ShowAllResponse<ResearchShowToRegistrationResponse> showAllToRegistration(ShowAllRequest showAllRequest) {
        int page = showAllRequest.getPage();
        int limit = showAllRequest.getLimit();
        int totalPages = (int) Math.ceil((1.0 * countAll()) / limit);

        Pageable paging = PageRequest.of(
                page - 1,
                limit,
                Sort.by(Sort.Direction
                        .fromString(showAllRequest.getOrderDirection()), showAllRequest.getOrderBy())
        );
        Page<ResearchEntity> researchEntityList = researchRepository.findAll(paging);
        List<ResearchEntity> resultEntity = researchEntityList.getContent();
        List<ResearchShowToRegistrationResponse> resultDTO =
                resultEntity.stream().map(mapper::toShowToRegistrationDTO).toList();

        return ShowAllResponse.<ResearchShowToRegistrationResponse>builder()
                .page(page)
                .totalPages(totalPages)
                .responses(resultDTO)
                .build();
    }

    @Override
    public void registerResearch(ResearchRegisterInput researchRegisterInput) {
        researchRegisterInput.setStudentID(userService.getClaimsToken().get("sub").toString());

        String researchID = researchRegisterInput.getResearchID();
        ResearchEntity researchEntity = researchRepository.findById(researchID)
                .orElseThrow(() -> new AppException(ErrorCode.RESEARCH_NOT_EXISTED));

        String studentID = researchRegisterInput.getStudentID();
        StudentEntity studentEntity = studentRepository.findById(studentID)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));

        researchEntity.setGroups(studentEntity.getGroups());

        researchEntity.setStatus(StatusResearch.valueOf(StatusResearchConst.ASSIGNED));
        researchRepository.save(researchEntity);
    }

    @Override
    public void cancelRegistrationResearch(ResearchCancelRegistrationInput researchCancelRegistrationInput) {
        String researchID = researchCancelRegistrationInput.getResearchID();
        ResearchEntity researchEntity = researchRepository.findById(researchID)
                .orElseThrow(() -> new AppException(ErrorCode.RESEARCH_NOT_EXISTED));

        researchEntity.setGroups(null);

        researchEntity.setStatus(StatusResearch.valueOf(StatusResearchConst.APPROVED));
        researchRepository.save(researchEntity);
    }

    private String generateResearchCode(ResearchInput researchInput) {
        StringBuilder code = new StringBuilder();
        code.append("KLKS");

        code.append("Đ");
        code.append(researchInput.getStage());

        String[] schoolYears = researchInput.getSchoolYear().split(("-"));
        code.append(schoolYears[0], 2, 4);
        code.append(schoolYears[1], 2, 4);

        String[] allLetterName = researchInput.getName().toUpperCase().split(" ");
        Arrays.stream(allLetterName).toList().forEach(l -> code.append(l, 0, 1));

        return code.toString();
    }
}
