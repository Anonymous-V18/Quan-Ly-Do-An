package com.hcv.service.impl;

import com.hcv.constant.TeacherPositionConst;
import com.hcv.converter.ITeacherMapper;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.TeacherFromExcelInput;
import com.hcv.dto.request.TeacherInput;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.TeacherDTO;
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
    ISubjectRepository subjectRepository;

    @Override
    public void checkDataBeforeInsert(TeacherFromExcelInput teacherFromExcelInput) {
        for (TeacherInput teacherInput : teacherFromExcelInput.getTeachers()) {
            boolean isTeacherExisted = teacherRepository.existsByMaSo(teacherInput.getMaSo().trim());
            if (isTeacherExisted) {
                throw new AppException(ErrorCode.TEACHER_EXISTED);
            }
            boolean isSubjectExisted = subjectRepository.existsByName(teacherInput.getSubjectName().trim());
            if (!isSubjectExisted) {
                throw new AppException(ErrorCode.SUBJECT_NOT_EXISTED);
            }

            List<String> listNameRole = List.of(teacherInput.getChucVu().split(","));
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
    public TeacherDTO insert(TeacherInput teacherInput) {
        TeacherEntity teacherEntity = teacherRepository.findOneByMaSo(teacherInput.getMaSo());
        if (teacherEntity != null) {
            throw new AppException(ErrorCode.TEACHER_EXISTED);
        }

        teacherEntity = teacherMapper.toEntity(teacherInput);

        UserEntity userEntity = userRepository.findOneById(teacherInput.getUserId());
        if (userEntity == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        teacherEntity.setUsers(userEntity);

        SubjectEntity subjectEntity = subjectRepository.findOneByName(teacherInput.getSubjectName());
        if (subjectEntity == null) {
            throw new AppException(ErrorCode.SUBJECT_NOT_EXISTED);
        }
        teacherEntity.setSubjects(subjectEntity);

        DepartmentEntity departmentEntity = subjectEntity.getDepartments();
        teacherEntity.setDepartments(departmentEntity);

        teacherRepository.save(teacherEntity);

        return teacherMapper.toDTO(teacherEntity);
    }

    @Override
    public TeacherDTO update(String oldTeacherId, TeacherInput teacherInput) {
        TeacherEntity teacherEntity = teacherRepository.findOneById(oldTeacherId);
        if (teacherEntity == null) {
            throw new AppException(ErrorCode.TEACHER_NOT_EXISTED);
        }

        teacherEntity.setMaSo(teacherInput.getMaSo());
        teacherEntity.setName(teacherInput.getName());
        teacherEntity.setHocVi(teacherInput.getHocVi());
        teacherEntity.setChucVu(teacherInput.getChucVu());
        teacherEntity.setEmail(teacherInput.getEmail());
        teacherEntity.setPhoneNumber(teacherInput.getPhoneNumber());

        SubjectEntity subjectEntity = subjectRepository.findOneByName(teacherInput.getSubjectName());
        if (subjectEntity == null) {
            throw new AppException(ErrorCode.SUBJECT_NOT_EXISTED);
        }
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
    public TeacherDTO findOneByMaSo(String maSo) {
        TeacherEntity teacher = teacherRepository.findOneByMaSo(maSo);
        return teacher == null ? null : teacherMapper.toDTO(teacher);
    }

    @Override
    public TeacherDTO findOneById(String id) {
        TeacherEntity teacher = teacherRepository.findOneById(id);
        return teacher == null ? null : teacherMapper.toDTO(teacher);
    }

    @Override
    public List<TeacherDTO> findAll() {
        List<TeacherEntity> result = teacherRepository.findAll();
        return result.stream().map(teacherMapper::toDTO).toList();
    }


}
