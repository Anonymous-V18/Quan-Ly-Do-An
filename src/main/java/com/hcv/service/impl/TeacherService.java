package com.hcv.service.impl;

import com.hcv.converter.ITeacherMapper;
import com.hcv.dto.CodeRole;
import com.hcv.dto.request.*;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.TeacherDTO;
import com.hcv.dto.response.TeacherShowToSelectionResponse;
import com.hcv.dto.response.UserDTO;
import com.hcv.entity.Department;
import com.hcv.entity.Subject;
import com.hcv.entity.Teacher;
import com.hcv.entity.User;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IRoleRepository;
import com.hcv.repository.ISubjectRepository;
import com.hcv.repository.ITeacherRepository;
import com.hcv.repository.IUserRepository;
import com.hcv.service.ITeacherService;
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

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TeacherService implements ITeacherService {

    ITeacherRepository teacherRepository;
    ITeacherMapper teacherMapper;
    IUserRepository userRepository;
    IUserService userService;
    ISubjectRepository subjectRepository;
    IRoleRepository roleRepository;

    @Override
    @Transactional
    public List<TeacherDTO> insertFromFile(TeacherInsertFromFileInput teacherInsertFromFileInput) {
        List<TeacherDTO> teacherDTOList = new ArrayList<>();
        for (TeacherInput teacherInput : teacherInsertFromFileInput.getTeachers()) {

            String usernameAndPasswordDefault = teacherInput.getCode().trim();
            UserRequest userRequest = new UserRequest();
            userRequest.setUsername(usernameAndPasswordDefault);
            userRequest.setPassword(usernameAndPasswordDefault);

            userRequest.setRoleIds(teacherInput.getRoleIds());
            userRequest.setIsActivated(0);

            UserDTO userDTO = userService.create(userRequest);

            teacherInput.setUserId(userDTO.getId());

            teacherDTOList.add(this.insert(teacherInput));
        }
        return teacherDTOList;
    }

    @Override
    public TeacherDTO insert(TeacherInput teacherInput) {
        teacherRepository.findByCode(teacherInput.getCode())
                .ifPresent(item -> {
                    throw new AppException(ErrorCode.TEACHER_EXISTED);
                });

        Teacher teacher = teacherMapper.toEntity(teacherInput);

        User user = userRepository.findById(teacherInput.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        teacher.setUser(user);

        Subject subject = subjectRepository.findById(teacherInput.getSubjectId())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));
        teacher.setSubject(subject);

        Department department = subject.getDepartment();
        teacher.setDepartment(department);

        teacher = teacherRepository.save(teacher);

        return teacherMapper.toDTO(teacher);
    }

    @Override
    public TeacherDTO update(String oldTeacherId, TeacherNormalUpdateInput teacherInput) {
        Teacher teacher = teacherRepository.findById(oldTeacherId)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED));

        teacher.setEmail(teacherInput.getEmail());
        teacher.setPhoneNumber(teacherInput.getPhoneNumber());

        teacherRepository.save(teacher);

        return teacherMapper.toDTO(teacher);
    }

    @Override
    public TeacherDTO updateAdvanced(String oldTeacherId, TeacherInput teacherInput) {
        Teacher teacher = teacherRepository.findById(oldTeacherId)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED));

        teacherRepository.findByCodeAndIdNot(teacherInput.getCode(), oldTeacherId)
                .ifPresent(item -> {
                    throw new AppException(ErrorCode.TEACHER_EXISTED);
                });

        User user = teacher.getUser();
        user = userService.updateRoles(user, teacherInput.getRoleIds());
        teacher.setUser(user);

        teacher = teacherMapper.toEntity(teacher, teacherInput);

        Subject subject = subjectRepository.findById(teacherInput.getSubjectId())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));
        teacher.setSubject(subject);

        teacher.setDepartment(teacher.getSubject().getDepartment());

        teacherRepository.save(teacher);

        return teacherMapper.toDTO(teacher);
    }


    @Override
    public void delete(String[] ids) {
        List<Teacher> teacherList = teacherRepository.findAllById(Arrays.stream(ids).toList());
        teacherList = teacherList.stream().filter(Objects::nonNull).toList();

        List<User> userList = new ArrayList<>();
        teacherList.forEach(teacherEntity -> userList.add(teacherEntity.getUser()));

        teacherRepository.deleteAll(teacherList);
        userRepository.deleteAll(userList);

    }

    @Override
    public int count() {
        return (int) teacherRepository.count();
    }

    @Override
    public ShowAllResponse<TeacherDTO> showAll(ShowAllRequest showAllRequest) {
        int page = showAllRequest.getCurrentPage();
        int limit = showAllRequest.getLimit();
        Pageable paging = PageRequest.of(
                page - 1,
                limit,
                Sort.by(Sort.Direction.fromString(showAllRequest.getOrderDirection()), showAllRequest.getOrderBy())
        );
        Page<Teacher> teacherEntityList = teacherRepository.findAll(paging);
        List<TeacherDTO> resultDTO = teacherEntityList.getContent().stream().map(teacherMapper::toDTO).toList();

        int totalElements = (int) teacherRepository.count();
        int totalPages = (int) Math.ceil((1.0 * totalElements) / limit);

        return ShowAllResponse.<TeacherDTO>builder()
                .currentPage(page)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .responses(resultDTO)
                .build();
    }

    @Override
    public List<TeacherShowToSelectionResponse> showAllToSelection(Boolean theSameSubject, String subjectId) {
        String teacherId = userService.getClaimsToken().get("sub").toString();
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED));
        Subject subject = Optional.of(teacher.getSubject())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));
        if (theSameSubject) {
            return teacherRepository.findAllBySubject_Id(subject.getId()).stream()
                    .map(teacherMapper::toShowDTOToSelection)
                    .toList();
        } else if (!subjectId.equals("EMPTY")) {
            return teacherRepository.findAllBySubject_Id(subjectId).stream()
                    .map(teacherMapper::toShowDTOToSelection)
                    .toList();
        }
        return teacherRepository.findAllBySubject_Department_Id(subject.getDepartment().getId()).stream()
                .map(teacherMapper::toShowDTOToSelection).toList();
    }

    @Override
    public TeacherDTO findOneById(String id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED));
        return teacherMapper.toDTO(teacher);
    }

    @Override
    public List<TeacherDTO> findAll() {
        List<Teacher> result = teacherRepository.findAll();
        return result.stream().map(teacherMapper::toDTO).toList();
    }

    @Override
    public List<TeacherShowToSelectionResponse> showAllHeadOfDepartment() {
        String teacherId = userService.getClaimsToken().get("sub").toString();
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED));
        String departmentId = Optional.of(teacher.getDepartment())
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED))
                .getId();
        return teacherRepository
                .findAllByUser_Roles_CodeAndDepartment_Id(CodeRole.HEAD_OF_DEPARTMENT, departmentId)
                .stream()
                .map(teacherMapper::toShowDTOToSelection)
                .toList();
    }

}
