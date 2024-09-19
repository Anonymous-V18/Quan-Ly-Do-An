package com.hcv.service.impl;

import com.hcv.constant.StatusResearchConst;
import com.hcv.converter.IResearchMapper;
import com.hcv.dto.StatusResearch;
import com.hcv.dto.request.*;
import com.hcv.dto.response.ResearchDTO;
import com.hcv.dto.response.ResearchResponse;
import com.hcv.dto.response.ResearchShowToRegistrationResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.entity.*;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IResearchRepository;
import com.hcv.repository.IStudentRepository;
import com.hcv.repository.ITeacherRepository;
import com.hcv.repository.IUserRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResearchService implements IResearchService {

    ITeacherRepository teacherRepository;
    IResearchRepository researchRepository;
    IResearchMapper mapper;
    IStudentRepository studentRepository;
    IUserService userService;
    IUserRepository userRepository;

    @Override
    @Transactional
    public List<ResearchDTO> insertFromFile(ResearchInsertFromFileInput researchInsertFromFileInput) {
        return researchInsertFromFileInput.getResearches().stream().map(this::insert).toList();
    }

    @Override
    public ResearchDTO insert(ResearchInput researchInput) {
        researchInput.setCode(this.generateResearchCode(researchInput));

        Research research = mapper.toEntity(researchInput);

        List<String> teacherIds = researchInput.getInstructorsIds();
        String creatorId = userService.getClaimsToken().get("sub").toString();
        teacherIds.add(creatorId);
        List<Teacher> teachers = teacherIds.stream()
                .map(teacherId -> teacherRepository.findById(teacherId)
                        .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED)))
                .toList();

        research.setInstructorsIds(teacherIds);
        research.setTeachers(teachers);

        List<Subject> subjects = teachers.stream()
                .map(Teacher::getSubjects)
                .distinct()
                .toList();

        if (subjects.contains(null)) {
            throw new AppException(ErrorCode.SUBJECT_NOT_EXISTED);
        }

        research.setSubjects(subjects);

        research.setStatus(StatusResearch.valueOf(StatusResearchConst.PENDING_APPROVE));

        research = researchRepository.save(research);

        return mapper.toDTO(research);
    }

    @Override
    public ResearchDTO update(String oldResearchId, ResearchUpdateInput newResearchUpdateInput) {
        Research research = researchRepository.findById(oldResearchId)
                .orElseThrow(() -> new AppException(ErrorCode.RESEARCH_NOT_EXISTED));

        String currentUserId = userService.getClaimsToken().get("sub").toString();
        if (!research.getInstructorsIds().contains(currentUserId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        research = mapper.toEntity(research, newResearchUpdateInput);

        List<String> newTeacherIds = new ArrayList<>();

        List<String> newInstructorsIds = newResearchUpdateInput.getInstructorsIds();
        if (!new HashSet<>(research.getInstructorsIds()).containsAll(newInstructorsIds)) {
            newTeacherIds.addAll(newInstructorsIds);
        }

        String newThesisAdvisor = newResearchUpdateInput.getThesisAdvisorId();
        if (newThesisAdvisor != null && !newThesisAdvisor.equals(research.getThesisAdvisorId().trim())) {
            newTeacherIds.add(newThesisAdvisor);
        }

        if (!newTeacherIds.isEmpty()) {
            List<Teacher> teachers = newTeacherIds.stream()
                    .map(teacherId -> teacherRepository.findById(teacherId)
                            .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED)))
                    .toList();

            research.setInstructorsIds(newResearchUpdateInput.getInstructorsIds());
            research.setThesisAdvisorId(newThesisAdvisor);
            research.setTeachers(teachers);

            List<Subject> subjects = teachers.stream()
                    .map(Teacher::getSubjects)
                    .distinct()
                    .toList();
            if (subjects.contains(null)) {
                throw new AppException(ErrorCode.SUBJECT_NOT_EXISTED);
            }
            research.setSubjects(subjects);
        }

        research.setCode(this.generateResearchCode(
                ResearchInput.builder()
                        .name(newResearchUpdateInput.getName())
                        .stage(newResearchUpdateInput.getStage())
                        .schoolYear(newResearchUpdateInput.getSchoolYear())
                        .build())
        );

        research = researchRepository.save(research);

        return mapper.toDTO(research);
    }

    @Override
    public ResearchDTO markApproved(String id) {
        Research research = researchRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESEARCH_NOT_EXISTED));

        research.setStatus(StatusResearch.valueOf(StatusResearchConst.APPROVED));
        researchRepository.save(research);

        return mapper.toDTO(research);
    }


    @Override
    public void delete(String[] ids) {
        List<Research> researchList = researchRepository.findAllById(Arrays.asList(ids));
        researchList.forEach(researchEntity ->
                researchEntity.setStatus(StatusResearch.valueOf(StatusResearchConst.DELETED)));

        researchRepository.saveAll(researchList);
    }

    @Override
    public ResearchDTO findOneById(String id) {
        Research research = researchRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESEARCH_NOT_EXISTED));
        return mapper.toDTO(research);
    }

    @Override
    public ResearchResponse showDetail(String id) {
        Research research = researchRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESEARCH_NOT_EXISTED));

        if (research.getStatus().equals(StatusResearch.valueOf(StatusResearchConst.ASSIGNED))) {
            return mapper.toShowDTOAfterApproved(research);
        }

        return mapper.toShowDTO(research);

    }

    @Override
    public ShowAllResponse<ResearchResponse> showAllMyResearch(ShowAllRequest showAllRequest) {
        String currentUserId = userService.getClaimsToken().get("sub").toString();
        List<StatusResearch> statusList = List.of(
                StatusResearch.valueOf(StatusResearchConst.PENDING_APPROVE)
        );

        Pageable paging = PageRequest.of(
                showAllRequest.getCurrentPage() - 1,
                showAllRequest.getLimit(),
                Sort.by(Sort.Direction
                        .fromString(showAllRequest.getOrderDirection()), showAllRequest.getOrderBy())
        );

        Page<Research> researchEntityList = researchRepository.findByTeachers_Id(currentUserId, paging);
        List<ResearchResponse> resultDTO = researchEntityList.getContent().stream()
                .map(mapper::toShowDTO)
                .toList();

        int page = showAllRequest.getCurrentPage();
        int limit = showAllRequest.getLimit();
        int totalElements = !resultDTO.isEmpty()
                ? this.countByStatusInAndSubjectsId(statusList, resultDTO.getFirst().getSubjects().getFirst().getId())
                : 0;
        int totalPages = (int) Math.ceil((1.0 * totalElements) / limit);

        return ShowAllResponse.<ResearchResponse>builder()
                .currentPage(page)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .responses(resultDTO)
                .build();
    }

    @Override
    public ShowAllResponse<ResearchResponse> showAllToFeedback(ShowAllRequest showAllRequest) {
        List<StatusResearch> statusList = List.of(
                StatusResearch.valueOf(StatusResearchConst.PENDING_APPROVE)
        );

        Page<Research> researchEntityList = showAllBase(showAllRequest, statusList);
        List<ResearchResponse> resultDTO = researchEntityList.getContent().stream()
                .map(mapper::toShowDTO)
                .toList();

        int page = showAllRequest.getCurrentPage();
        int limit = showAllRequest.getLimit();
        int totalElements = !resultDTO.isEmpty()
                ? this.countByStatusInAndSubjectsId(statusList, resultDTO.getFirst().getSubjects().getFirst().getId())
                : 0;
        int totalPages = (int) Math.ceil((1.0 * totalElements) / limit);

        return ShowAllResponse.<ResearchResponse>builder()
                .currentPage(page)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .responses(resultDTO)
                .build();
    }

    @Override
    public ShowAllResponse<ResearchShowToRegistrationResponse> showAllToRegistration(ShowAllRequest showAllRequest) {
        List<StatusResearch> statusList = List.of(
                StatusResearch.valueOf(StatusResearchConst.APPROVED)
        );

        Page<Research> researchEntityList = showAllBase(showAllRequest, statusList);
        List<ResearchShowToRegistrationResponse> resultDTO =
                researchEntityList.getContent().stream()
                        .map(mapper::toShowToRegistrationDTO)
                        .toList();

        int page = showAllRequest.getCurrentPage();
        int limit = showAllRequest.getLimit();
        int totalElements = !resultDTO.isEmpty()
                ? this.countByStatusInAndSubjectsId(statusList, resultDTO.getFirst().getSubjects().getFirst().getId())
                : 0;
        int totalPages = (int) Math.ceil((1.0 * totalElements) / limit);

        return ShowAllResponse.<ResearchShowToRegistrationResponse>builder()
                .currentPage(page)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .responses(resultDTO)
                .build();
    }

    @Override
    public ShowAllResponse<ResearchShowToRegistrationResponse> showAllToApprovalProcessing(ShowAllRequest showAllRequest) {
        List<StatusResearch> statusList = List.of(
                StatusResearch.valueOf(StatusResearchConst.PENDING_APPROVE),
                StatusResearch.valueOf(StatusResearchConst.APPROVED)
        );

        Page<Research> researchEntityList = showAllBase(showAllRequest, statusList);
        List<ResearchShowToRegistrationResponse> resultDTO =
                researchEntityList.getContent().stream()
                        .map(mapper::toShowToRegistrationDTO)
                        .toList();

        int page = showAllRequest.getCurrentPage();
        int limit = showAllRequest.getLimit();
        int totalElements = !resultDTO.isEmpty()
                ? this.countByStatusInAndSubjectsId(statusList, resultDTO.getFirst().getSubjects().getFirst().getId())
                : 0;
        int totalPages = (int) Math.ceil((1.0 * totalElements) / limit);

        return ShowAllResponse.<ResearchShowToRegistrationResponse>builder()
                .currentPage(page)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .responses(resultDTO)
                .build();
    }

    @Override
    public ResearchDTO cancelApproval(String id) {
        Research research = researchRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESEARCH_NOT_EXISTED));

        if (research.getStatus().equals(StatusResearch.valueOf(StatusResearchConst.APPROVED))) {
            research.setStatus(StatusResearch.valueOf(StatusResearchConst.PENDING_APPROVE));
            research = researchRepository.save(research);
            return mapper.toDTO(research);
        }

        return mapper.toDTO(research);
    }

    @Override
    public void registerResearch(ResearchRegisterInput researchRegisterInput) {
        researchRegisterInput.setStudentID(userService.getClaimsToken().get("sub").toString());

        String researchID = researchRegisterInput.getResearchID();
        Research research = researchRepository.findById(researchID)
                .orElseThrow(() -> new AppException(ErrorCode.RESEARCH_NOT_EXISTED));
        if (research.getStatus().name().equals("AS")) {
            throw new AppException(ErrorCode.RESEARCH_HAS_BEEN_ASSIGNED);
        }

        String studentID = researchRegisterInput.getStudentID();
        Student student = studentRepository.findById(studentID)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));
        if (student.getGroups() == null) {
            throw new AppException(ErrorCode.STUDENT_HAS_NOT_GROUP);
        }
        if (student.getGroups().getResearches() != null) {
            throw new AppException(ErrorCode.STUDENT_EXISTED_IN_OTHER_RESEARCH);
        }

        research.setGroups(student.getGroups());

        research.setStatus(StatusResearch.valueOf(StatusResearchConst.ASSIGNED));
        researchRepository.save(research);
    }

    @Override
    public void cancelRegistrationResearch(ResearchCancelRegistrationInput researchCancelRegistrationInput) {
        String researchID = researchCancelRegistrationInput.getResearchID();
        Research research = researchRepository.findById(researchID)
                .orElseThrow(() -> new AppException(ErrorCode.RESEARCH_NOT_EXISTED));

        research.getGroups().setResearches(null);
        research.setGroups(null);

        research.setStatus(StatusResearch.valueOf(StatusResearchConst.APPROVED));
        researchRepository.save(research);
    }

    @Override
    public int countByStatusInAndSubjectsId(Collection<StatusResearch> statuses, String id) {
        return (int) researchRepository.countByStatusInAndSubjects_Id(statuses, id);
    }

    @Override
    public int countByTeachersId(String id) {
        return (int) researchRepository.countByTeachers_Id(id);
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

        return Normalizer.normalize(code.toString(), Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
    }

    private Page<Research> showAllBase(ShowAllRequest showAllRequest, List<StatusResearch> statusList) {
        String currentUserName = userService.getClaimsToken().get("username").toString();
        User user = userRepository.findByUsername(currentUserName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String subjectId = user.getStudents() != null
                ? user.getStudents().getSubjects().getId()
                : user.getTeachers().getSubjects().getId();


        Pageable paging = PageRequest.of(
                showAllRequest.getCurrentPage() - 1,
                showAllRequest.getLimit(),
                Sort.by(Sort.Direction
                        .fromString(showAllRequest.getOrderDirection()), showAllRequest.getOrderBy())
        );

        return researchRepository.findByStatusInAndSubjects_Id(statusList, subjectId, paging);
    }

}
