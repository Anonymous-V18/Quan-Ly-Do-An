package com.hcv.service.impl;

import com.hcv.converter.IStudentMapper;
import com.hcv.dto.StudentDTO;
import com.hcv.dto.SubjectDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.StudentInput;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.entity.DepartmentEntity;
import com.hcv.entity.StudentEntity;
import com.hcv.entity.SubjectEntity;
import com.hcv.entity.UserEntity;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IStudentRepository;
import com.hcv.repository.ISubjectRepository;
import com.hcv.repository.IUserRepository;
import com.hcv.service.IStudentService;
import com.hcv.service.ISubjectService;
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
public class StudentService implements IStudentService {

    IStudentRepository studentRepository;
    IStudentMapper studentMapper;
    ISubjectRepository subjectRepository;
    IUserRepository userRepository;
    ISubjectService subjectService;

    @Override
    public StudentDTO insert(StudentInput studentInput) {

        StudentEntity studentEntity = studentRepository.findOneByMaSo(studentInput.getMaSo());
        if (studentEntity != null) {
            throw new AppException(ErrorCode.STUDENT_EXISTED);
        }

        studentEntity = studentMapper.toEntity(studentInput);

        UserEntity userEntity = userRepository.findOneById(studentInput.getUser_id());
        if (userEntity == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        studentEntity.setUsers(userEntity);

        SubjectEntity subjectEntity = subjectRepository.findOneByName(studentInput.getSubjectName());
        if (subjectEntity == null) {
            throw new AppException(ErrorCode.SUBJECT_NOT_EXISTED);
        }
        studentEntity.setSubjects(subjectEntity);

        DepartmentEntity departmentEntity = subjectEntity.getDepartments();
        studentEntity.setDepartments(departmentEntity);

        studentRepository.save(studentEntity);

        return studentMapper.toDTO(studentEntity);
    }

    @Override
    public StudentDTO update(StudentDTO old_studentDTO, StudentInput studentInput) {
        old_studentDTO = studentMapper.toDTO(old_studentDTO, studentInput);

        SubjectDTO subjectDTO = subjectService.findOneByName(studentInput.getSubjectName());
        if (subjectDTO == null) {
            throw new AppException(ErrorCode.SUBJECT_NOT_EXISTED);
        }
        old_studentDTO.setSubjects(subjectDTO);

        StudentEntity studentEntity = studentMapper.toEntity(old_studentDTO);
        studentEntity.setDepartments(studentEntity.getSubjects().getDepartments());

        studentRepository.save(studentEntity);

        return studentMapper.toDTO(studentEntity);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            studentRepository.deleteById(id);
        }
    }

    @Override
    public StudentDTO findOneById(Long id) {
        StudentEntity student = studentRepository.findOneById(id);
        return student == null ? null : studentMapper.toDTO(student);
    }


    @Override
    public StudentDTO findOneByMaSo(String maSo) {
        StudentEntity student = studentRepository.findOneByMaSo(maSo);
        return student == null ? null : studentMapper.toDTO(student);
    }

    @Override
    public int countAll() {
        return (int) studentRepository.count();
    }

    @Override
    public ShowAllResponse<StudentDTO> showAll(ShowAllRequest showAllRequest) {
        int page = showAllRequest.getPage();
        int limit = showAllRequest.getLimit();
        int totalPages = (int) Math.ceil((1.0 * countAll()) / limit);

        Pageable paging = PageRequest.of(
                page - 1,
                limit,
                Sort.by(Sort.Direction.fromString(showAllRequest.getOrderDirection()), showAllRequest.getOrderBy())
        );
        Page<StudentEntity> studentEntityList = studentRepository.findAll(paging);

        List<StudentEntity> resultEntity = studentEntityList.getContent();

        List<StudentDTO> resultDTO = resultEntity.stream().map(studentMapper::toDTO).toList();

        return ShowAllResponse.<StudentDTO>builder()
                .page(page)
                .totalPages(totalPages)
                .responses(resultDTO)
                .build();

    }

    @Override
    public List<StudentDTO> findAll() {
        List<StudentEntity> resultEntity = studentRepository.findAll();
        return resultEntity.stream().map(studentMapper::toDTO).toList();
    }
}
