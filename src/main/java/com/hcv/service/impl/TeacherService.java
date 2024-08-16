package com.hcv.service.impl;

import com.hcv.constant.TeacherPositionConst;
import com.hcv.converter.ITeacherMapper;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.TeacherInput;
import com.hcv.dto.request.TeacherInsertFromFileInput;
import com.hcv.dto.request.UserRequest;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.TeacherDTO;
import com.hcv.dto.response.TeacherShowToSelectionResponse;
import com.hcv.dto.response.UserDTO;
import com.hcv.entity.DepartmentEntity;
import com.hcv.entity.SubjectEntity;
import com.hcv.entity.TeacherEntity;
import com.hcv.entity.UserEntity;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
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

    @Override
    public void checkDataBeforeInsert(TeacherInsertFromFileInput teacherInsertFromFileInput) {
        for (TeacherInput teacherInput : teacherInsertFromFileInput.getTeachers()) {
            boolean isTeacherExisted = teacherRepository.existsByCode(teacherInput.getCode().trim());
            if (isTeacherExisted) {
                throw new AppException(ErrorCode.TEACHER_EXISTED);
            }
            boolean isSubjectExisted = subjectRepository.existsByName(teacherInput.getSubjectName().trim());
            if (!isSubjectExisted) {
                throw new AppException(ErrorCode.SUBJECT_NOT_EXISTED);
            }

            List<String> listNameRole = teacherInput.getPosition();
            boolean checkNameRoleContain = List.of(TeacherPositionConst.TEACHER, TeacherPositionConst.DEAN,
                            TeacherPositionConst.CATECHISM, TeacherPositionConst.STUDENT,
                            TeacherPositionConst.HEAD_OF_DEPARTMENT)
                    .containsAll(listNameRole);
            if (!checkNameRoleContain) {
                throw new AppException(ErrorCode.INVALID_NAME_ROLE);
            }
        }
    }

    @Override
    public List<TeacherDTO> insertFromFile(TeacherInsertFromFileInput teacherInsertFromFileInput) {
        this.checkDataBeforeInsert(teacherInsertFromFileInput);
        List<TeacherDTO> teacherDTOList = new ArrayList<>();
        for (TeacherInput teacherInput : teacherInsertFromFileInput.getTeachers()) {

            String usernameAndPasswordDefault = teacherInput.getCode().trim();
            UserRequest userRequest = new UserRequest();
            userRequest.setUsername(usernameAndPasswordDefault);
            userRequest.setPassword(usernameAndPasswordDefault);
            userRequest.setNameRoles(teacherInput.getPosition());
            userRequest.setIsGraduate(1);

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

        TeacherEntity teacherEntity = teacherMapper.toEntity(teacherInput);

        UserEntity userEntity = userRepository.findById(teacherInput.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        teacherEntity.setUsers(userEntity);

        SubjectEntity subjectEntity = subjectRepository.findByName(teacherInput.getSubjectName())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));

        teacherEntity.setSubjects(subjectEntity);

        DepartmentEntity departmentEntity = subjectEntity.getDepartments();
        teacherEntity.setDepartments(departmentEntity);

        teacherEntity = teacherRepository.save(teacherEntity);

        return teacherMapper.toDTO(teacherEntity);
    }

    @Override
    public TeacherDTO update(String oldTeacherId, TeacherInput teacherInput) {
        TeacherEntity teacherEntity = teacherRepository.findById(oldTeacherId)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED));

        teacherEntity.setCode(teacherInput.getCode());
        teacherEntity.setName(teacherInput.getName());
        teacherEntity.setDegree(teacherInput.getDegree());
        teacherEntity.setPosition(teacherInput.getPosition());
        teacherEntity.setEmail(teacherInput.getEmail());
        teacherEntity.setPhoneNumber(teacherInput.getPhoneNumber());

        SubjectEntity subjectEntity = subjectRepository.findByName(teacherInput.getSubjectName())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));

        teacherEntity.setSubjects(subjectEntity);

        teacherEntity.setDepartments(teacherEntity.getSubjects().getDepartments());

        teacherRepository.save(teacherEntity);

        return teacherMapper.toDTO(teacherEntity);
    }

    @Override
    public void delete(String[] ids) {
        List<TeacherEntity> teacherEntityList = teacherRepository.findAllById(Arrays.stream(ids).toList());
        teacherEntityList = teacherEntityList.stream().filter(Objects::nonNull).toList();

        List<UserEntity> userEntityList = new ArrayList<>();
        teacherEntityList.forEach(teacherEntity -> userEntityList.add(teacherEntity.getUsers()));

        teacherRepository.deleteAll(teacherEntityList);
        userRepository.deleteAll(userEntityList);

    }

    @Override
    public int countAll() {
        return (int) teacherRepository.count();
    }

    @Override
    public ShowAllResponse<TeacherDTO> showAll(ShowAllRequest showAllRequest) {
        int page = showAllRequest.getPage();
        int limit = showAllRequest.getLimit();
        int totalPages = (int) Math.ceil((1.0 * countAll()) / limit);

        Pageable paging = PageRequest.of(
                page - 1,
                limit,
                Sort.by(Sort.Direction.fromString(showAllRequest.getOrderDirection()), showAllRequest.getOrderBy())
        );
        Page<TeacherEntity> teacherEntityList = teacherRepository.findAll(paging);
        List<TeacherEntity> resultEntity = teacherEntityList.getContent();
        List<TeacherDTO> resultDTO = resultEntity.stream().map(teacherMapper::toDTO).toList();

        return ShowAllResponse.<TeacherDTO>builder()
                .page(page)
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
        TeacherEntity teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED));
        return teacherMapper.toDTO(teacher);
    }

    @Override
    public List<TeacherDTO> findAll() {
        List<TeacherEntity> result = teacherRepository.findAll();
        return result.stream().map(teacherMapper::toDTO).toList();
    }


}
