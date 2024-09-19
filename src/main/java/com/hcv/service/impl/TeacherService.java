package com.hcv.service.impl;

import com.hcv.converter.ITeacherMapper;
import com.hcv.dto.request.*;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.TeacherDTO;
import com.hcv.dto.response.TeacherShowToSelectionResponse;
import com.hcv.dto.response.UserDTO;
import com.hcv.entity.*;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

            if (!teacherInput.getPosition().contains("GIẢNG VIÊN")) {
                teacherInput.getPosition().add("GIẢNG VIÊN");
            }

            userRequest.setNameRoles(teacherInput.getPosition());
            userRequest.setIsGraduate(0);

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
        teacher.setUsers(user);

        Subject subject = subjectRepository.findByName(teacherInput.getSubjectName())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));
        teacher.setSubjects(subject);

        Department department = subject.getDepartments();
        teacher.setDepartments(department);

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

        teacher = teacherMapper.toEntity(teacher, teacherInput);

        List<RoleEntity> roles = roleRepository.findByNameIn(teacherInput.getPosition());
        if (roles.contains(null)) {
            throw new AppException(ErrorCode.INVALID_NAME_ROLE);
        }
        teacher.getUsers().setRoles(roles);

        Subject subject = subjectRepository.findByName(teacherInput.getSubjectName())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));
        teacher.setSubjects(subject);

        teacher.setDepartments(teacher.getSubjects().getDepartments());

        teacherRepository.save(teacher);

        return teacherMapper.toDTO(teacher);
    }


    @Override
    public void delete(String[] ids) {
        List<Teacher> teacherList = teacherRepository.findAllById(Arrays.stream(ids).toList());
        teacherList = teacherList.stream().filter(Objects::nonNull).toList();

        List<User> userList = new ArrayList<>();
        teacherList.forEach(teacherEntity -> userList.add(teacherEntity.getUsers()));

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
    public List<TeacherShowToSelectionResponse> showAllToSelection() {
        return teacherRepository.findAll().stream().map(teacherMapper::toShowDTOToSelection).toList();
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


}
