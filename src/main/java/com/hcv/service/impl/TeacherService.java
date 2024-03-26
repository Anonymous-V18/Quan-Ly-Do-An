package com.hcv.service.impl;

import com.hcv.converter.ITeacherMapper;
import com.hcv.dto.SubjectDTO;
import com.hcv.dto.TeacherDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.TeacherInput;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.entity.DepartmentEntity;
import com.hcv.entity.SubjectEntity;
import com.hcv.entity.TeacherEntity;
import com.hcv.entity.UserEntity;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.ISubjectRepository;
import com.hcv.repository.ITeacherRepository;
import com.hcv.repository.IUserRepository;
import com.hcv.service.ISubjectService;
import com.hcv.service.ITeacherService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TeacherService implements ITeacherService {

    ITeacherRepository teacherRepository;
    ITeacherMapper teacherMapper;
    IUserRepository userRepository;
    ISubjectRepository subjectRepository;
    ISubjectService subjectService;


    @Override
    public TeacherDTO insert(TeacherInput teacherInput) {

        TeacherEntity teacherEntity = teacherMapper.toEntity(teacherInput);

        UserEntity userEntity = userRepository.findOneById(teacherInput.getUser_id());
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
    public TeacherDTO update(TeacherDTO old_teacherDTO, TeacherInput teacherInput) {
        old_teacherDTO = teacherMapper.toDTO(old_teacherDTO, teacherInput);

        SubjectDTO subjectDTO = subjectService.findOneByName(teacherInput.getSubjectName());
        if (subjectDTO == null) {
            throw new AppException(ErrorCode.SUBJECT_NOT_EXISTED);
        }
        old_teacherDTO.setSubjects(subjectDTO);

        TeacherEntity teacherEntity = teacherMapper.toEntity(old_teacherDTO);
        teacherEntity.setDepartments(teacherEntity.getSubjects().getDepartments());

        teacherRepository.save(teacherEntity);

        return teacherMapper.toDTO(teacherEntity);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            teacherRepository.deleteById(id);
        }
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
    public List<TeacherDTO> findAll() {
        List<TeacherEntity> result = teacherRepository.findAll();
        return result.stream().map(teacherMapper::toDTO).toList();
    }


}
