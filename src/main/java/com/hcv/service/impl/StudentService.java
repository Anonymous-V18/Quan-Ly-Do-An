package com.hcv.service.impl;

import com.hcv.converter.IStudentMapper;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.StudentInput;
import com.hcv.dto.request.StudentInsertFromFileInput;
import com.hcv.dto.request.UserRequest;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.StudentDTO;
import com.hcv.dto.response.StudentShowToSelectionResponse;
import com.hcv.dto.response.UserDTO;
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
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentService implements IStudentService {

    IStudentRepository studentRepository;
    IStudentMapper studentMapper;
    ISubjectRepository subjectRepository;
    IUserRepository userRepository;
    IUserService userService;

    @Override
    public void checkDataBeforeInsert(StudentInsertFromFileInput studentInsertFromFileInput) {
        for (StudentInput studentInput : studentInsertFromFileInput.getStudents()) {
            boolean isStudentExisted = studentRepository.existsByCode(studentInput.getCode().trim());
            if (isStudentExisted) {
                throw new AppException(ErrorCode.STUDENT_EXISTED);
            }

            boolean isSubjectExisted = subjectRepository.existsByName(studentInput.getSubjectName().trim());
            if (!isSubjectExisted) {
                throw new AppException(ErrorCode.SUBJECT_NOT_EXISTED);
            }
        }
        List<String> studentCodeList = studentInsertFromFileInput.getStudents()
                .stream()
                .map(StudentInput::getCode)
                .distinct()
                .toList();
        if (studentCodeList.size() != studentInsertFromFileInput.getStudents().size()) {
            throw new AppException(ErrorCode.STUDENT_DUPLICATED);
        }
    }

    @Override
    public List<StudentDTO> insertFromFile(StudentInsertFromFileInput studentInsertFromFileInput) {
        this.checkDataBeforeInsert(studentInsertFromFileInput);
        List<StudentDTO> studentDTOList = new ArrayList<>();
        for (StudentInput studentInput : studentInsertFromFileInput.getStudents()) {

            String usernameAndPasswordDefault = studentInput.getCode().trim();
            UserRequest userRequest = new UserRequest();
            userRequest.setUsername(usernameAndPasswordDefault);
            userRequest.setPassword(usernameAndPasswordDefault);
            userRequest.setNameRoles(List.of("SINH VIÊN"));
            userRequest.setIsGraduate(0);

            UserDTO userDTO = userService.create(userRequest);

            studentInput.setUserId(userDTO.getId());

            studentDTOList.add(this.insert(studentInput));
        }
        return studentDTOList;
    }

    @Override
    public StudentDTO insert(StudentInput studentInput) {
        studentRepository.findByCode(studentInput.getCode().trim())
                .ifPresent(item -> {
                    throw new AppException(ErrorCode.STUDENT_EXISTED);
                });

        StudentEntity studentEntity = studentMapper.toEntity(studentInput);

        UserEntity userEntity = userRepository.findById(studentInput.getUserId().trim())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        studentEntity.setUsers(userEntity);

        SubjectEntity subjectEntity = subjectRepository.findByName(studentInput.getSubjectName().trim())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));

        studentEntity.setSubjects(subjectEntity);

        DepartmentEntity departmentEntity = subjectEntity.getDepartments();
        studentEntity.setDepartments(departmentEntity);

        studentEntity = studentRepository.save(studentEntity);

        return studentMapper.toDTO(studentEntity);
    }

    @Override
    public StudentDTO update(String oldStudentId, StudentInput studentInput) {
        StudentEntity studentEntity = studentRepository.findById(oldStudentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));

        studentEntity.setCode(studentInput.getCode());
        studentEntity.setName(studentInput.getName());
        studentEntity.setMyClass(studentInput.getMyClass());
        studentEntity.setEmail(studentInput.getEmail());
        studentEntity.setPhoneNumber(studentInput.getPhoneNumber());

        SubjectEntity subjectEntity = subjectRepository.findByName(studentInput.getSubjectName())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));

        studentEntity.setSubjects(subjectEntity);

        studentEntity.setDepartments(studentEntity.getSubjects().getDepartments());

        studentRepository.save(studentEntity);

        return studentMapper.toDTO(studentEntity);
    }

    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            studentRepository.deleteById(id);
        }
    }

    @Override
    public StudentDTO findOneById(String id) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));
        return studentMapper.toDTO(student);
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
    public List<StudentShowToSelectionResponse> showAllToSelection() {
        return studentRepository.findAll().stream().map(studentMapper::toShowToSelectionDTO).toList();
    }

    @Override
    public List<StudentDTO> findAll() {
        List<StudentEntity> resultEntity = studentRepository.findAll();
        return resultEntity.stream().map(studentMapper::toDTO).toList();
    }

}
