package com.hcv.service.impl;

import com.hcv.api_controller.input.TeacherInput;
import com.hcv.converter.TeacherConverter;
import com.hcv.dto.TeacherDTO;
import com.hcv.entity.DepartmentEntity;
import com.hcv.entity.SubjectEntity;
import com.hcv.entity.TeacherEntity;
import com.hcv.entity.UserEntity;
import com.hcv.repository.IDepartmentRepository;
import com.hcv.repository.ISubjectRepository;
import com.hcv.repository.ITeacherRepository;
import com.hcv.repository.IUserRepository;
import com.hcv.service.IDepartmentService;
import com.hcv.service.ISubjectService;
import com.hcv.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService implements ITeacherService {

    @Autowired
    private ITeacherRepository teacherRepository;
    @Autowired
    private TeacherConverter teacherConverter;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IDepartmentRepository departmentRepository;
    @Autowired
    private ISubjectRepository subjectRepository;
    @Autowired
    private ISubjectService subjectService;
    @Autowired
    private IDepartmentService departmentService;

    @Override
    public TeacherDTO insert(TeacherInput teacherInput) {

        TeacherEntity teacherEntity = teacherConverter.toEntity(teacherInput);

        UserEntity userEntity = userRepository.findOneById(teacherInput.getUser_id());
        teacherEntity.setUsers(userEntity);

        DepartmentEntity departmentEntity = departmentRepository.findOneByName(teacherInput.getDepartmentName());
        teacherEntity.setDepartments(departmentEntity);

        SubjectEntity subjectEntity = subjectRepository.findOneByName(teacherInput.getSubjectName());
        teacherEntity.setSubjects(subjectEntity);

        teacherRepository.save(teacherEntity);

        return teacherConverter.toDTO(teacherEntity);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            teacherRepository.deleteById(id);
        }
    }

    @Override
    public List<TeacherDTO> showAll() {
        List<TeacherEntity> resultEntity = teacherRepository.findAll();
        List<TeacherDTO> resultDTO = new ArrayList<>();
        for (TeacherEntity entity : resultEntity) {
            TeacherDTO dto = teacherConverter.toDTO(entity);
            resultDTO.add(dto);
        }
        return resultDTO;
    }

    @Override
    public TeacherDTO findOneByMaSo(String maSo) {
        TeacherEntity teacher = teacherRepository.findOneByMaSo(maSo);
        return teacher == null ? null : teacherConverter.toDTO(teacher);
    }


}
