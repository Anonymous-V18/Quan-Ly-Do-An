package com.hcv.service.impl;

import com.hcv.converter.IStudentMapper;
import com.hcv.dto.CodeRole;
import com.hcv.dto.request.*;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.StudentDTO;
import com.hcv.dto.response.StudentShowToSelectionResponse;
import com.hcv.dto.response.UserDTO;
import com.hcv.entity.*;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IRoleRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentService implements IStudentService {

    IStudentRepository studentRepository;
    IStudentMapper studentMapper;
    ISubjectRepository subjectRepository;
    IUserRepository userRepository;
    IUserService userService;
    IRoleRepository roleRepository;

    @Override
    @Transactional
    public List<StudentDTO> insertFromFile(StudentInsertFromFileInput studentInsertFromFileInput) {
        List<StudentDTO> studentDTOList = new ArrayList<>();
        Role role = roleRepository.findOneByCode(CodeRole.STUDENT)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_ROLE));
        for (StudentInput studentInput : studentInsertFromFileInput.getStudents()) {
            String usernameAndPasswordDefault = studentInput.getCode().trim();
            UserRequest userRequest = new UserRequest();
            userRequest.setUsername(usernameAndPasswordDefault);
            userRequest.setPassword(usernameAndPasswordDefault);
            userRequest.setRoleIds(Set.of(role.getId()));
            userRequest.setIsActivated(0);

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

        Student student = studentMapper.toEntity(studentInput);

        User user = userRepository.findById(studentInput.getUserId().trim())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        student.setUser(user);

        Subject subject = subjectRepository.findById(studentInput.getSubjectId().trim())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));
        student.setSubject(subject);

        Department department = subject.getDepartment();
        student.setDepartment(department);

        student = studentRepository.save(student);

        return studentMapper.toDTO(student);
    }

    @Override
    public StudentDTO update(String oldStudentId, StudentNormalUpdateInput studentInput) {
        Student student = studentRepository.findById(oldStudentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));

        student.setEmail(studentInput.getEmail());
        student.setPhoneNumber(studentInput.getPhoneNumber());

        studentRepository.save(student);

        return studentMapper.toDTO(student);
    }

    @Override
    public StudentDTO updateAdvanced(String oldStudentId, StudentInput studentInput) {
        Student student = studentRepository.findById(oldStudentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));

        studentRepository.findByCodeAndIdNot(studentInput.getCode(), oldStudentId)
                .ifPresent(item -> {
                    throw new AppException(ErrorCode.STUDENT_DUPLICATED);
                });

        student = studentMapper.toEntity(student, studentInput);

        Subject subject = subjectRepository.findById(studentInput.getSubjectId())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTED));
        student.setSubject(subject);

        student.setDepartment(student.getSubject().getDepartment());

        studentRepository.save(student);

        return studentMapper.toDTO(student);
    }

    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            studentRepository.deleteById(id);
        }
    }

    @Override
    public StudentDTO findOneById(String id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));
        return studentMapper.toDTO(student);
    }

    @Override
    public int count() {
        return (int) studentRepository.count();
    }

    @Override
    public ShowAllResponse<StudentDTO> showAll(ShowAllRequest showAllRequest) {
        int page = showAllRequest.getCurrentPage();
        int limit = showAllRequest.getLimit();
        Pageable paging = PageRequest.of(
                page - 1,
                limit,
                Sort.by(Sort.Direction.fromString(showAllRequest.getOrderDirection()), showAllRequest.getOrderBy())
        );
        Page<Student> studentEntityList = studentRepository.findAll(paging);
        List<StudentDTO> resultDTO = studentEntityList.getContent().stream().map(studentMapper::toDTO).toList();

        int totalElements = (int) studentRepository.count();
        int totalPages = (int) Math.ceil((1.0 * totalElements) / limit);

        return ShowAllResponse.<StudentDTO>builder()
                .currentPage(page)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .responses(resultDTO)
                .build();
    }

    @Override
    public List<StudentShowToSelectionResponse> showAllToSelection() {
        String currentUserId = userService.getClaimsToken().get("sub").toString();
        Student student = studentRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));

        return studentRepository.findStudentToInvite(currentUserId, List.of(student.getSubject()), 1)
                .stream().map(studentMapper::toShowToSelectionDTO)
                .toList();
    }

}
