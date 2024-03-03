package com.hcv.service.impl;

import com.hcv.api_controller.input.StudentInput;
import com.hcv.converter.StudentConverter;
import com.hcv.dto.StudentDTO;
import com.hcv.entity.DepartmentEntity;
import com.hcv.entity.StudentEntity;
import com.hcv.entity.SubjectEntity;
import com.hcv.entity.UserEntity;
import com.hcv.repository.IDepartmentRepository;
import com.hcv.repository.IStudentRepository;
import com.hcv.repository.ISubjectRepository;
import com.hcv.repository.IUserRepository;
import com.hcv.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService implements IStudentService {

    @Autowired
    private IStudentRepository studentRepository;
    @Autowired
    private StudentConverter studentConverter;
    @Autowired
    private ISubjectRepository subjectRepository;
    @Autowired
    private IDepartmentRepository departmentRepository;
    @Autowired
    private IUserRepository userRepository;

    @Override
    public void insert(StudentInput studentInput) {

        StudentEntity studentEntity = studentConverter.toEntity(studentInput);

        UserEntity userEntity = userRepository.findOneById(studentInput.getUser_id());
        studentEntity.setUsers(userEntity);

        SubjectEntity subjectEntity = subjectRepository.findOneByName(studentInput.getSubjectName());
        studentEntity.setSubjects(subjectEntity);

        DepartmentEntity departmentEntity = departmentRepository.findOneByName(studentInput.getDepartmentName());
        studentEntity.setDepartments(departmentEntity);

        studentRepository.save(studentEntity);
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
        return student == null ? null : studentConverter.toDTO(student);
    }

    @Override
    public List<StudentDTO> showAll() {
        List<StudentEntity> resultEntity = studentRepository.findAll();
        List<StudentDTO> resultDTO = new ArrayList<>();
        for (StudentEntity studentEntity : resultEntity) {
            StudentDTO studentDTO = studentConverter.toDTO(studentEntity);
            resultDTO.add(studentDTO);
        }
        return resultDTO;
    }
}
