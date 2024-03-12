package com.hcv.service.impl;

import com.hcv.converter.IStudentMapper;
import com.hcv.dto.StudentDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.StudentInput;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.entity.DepartmentEntity;
import com.hcv.entity.StudentEntity;
import com.hcv.entity.SubjectEntity;
import com.hcv.entity.UserEntity;
import com.hcv.repository.IStudentRepository;
import com.hcv.repository.ISubjectRepository;
import com.hcv.repository.IUserRepository;
import com.hcv.service.IStudentService;
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

    @Override
    public StudentDTO insert(StudentInput studentInput) {

        StudentEntity studentEntity = studentMapper.toEntity(studentInput);

        UserEntity userEntity = userRepository.findOneById(studentInput.getUser_id());
        studentEntity.setUsers(userEntity);

        SubjectEntity subjectEntity = subjectRepository.findOneByName(studentInput.getSubjectName());
        studentEntity.setSubjects(subjectEntity);

        DepartmentEntity departmentEntity = subjectEntity.getDepartments();
        studentEntity.setDepartments(departmentEntity);

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


}
