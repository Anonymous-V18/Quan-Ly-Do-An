package com.hcv.service.impl;

import com.hcv.api_controller.input.SubjectInput;
import com.hcv.converter.SubjectConverter;
import com.hcv.dto.SubjectDTO;
import com.hcv.entity.DepartmentEntity;
import com.hcv.entity.SubjectEntity;
import com.hcv.repository.IDepartmentRepository;
import com.hcv.repository.ISubjectRepository;
import com.hcv.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService implements ISubjectService {

    @Autowired
    private ISubjectRepository subjectRepository;
    @Autowired
    private SubjectConverter subjectConverter;
    @Autowired
    private IDepartmentRepository departmentRepository;

    @Override
    public SubjectDTO insert(SubjectInput subjectInput) {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName(subjectInput.getName());
        SubjectEntity subjectEntity = subjectConverter.toEntity(subjectDTO);
        DepartmentEntity departmentEntity = departmentRepository.findOneByName(subjectInput.getNameDepartment());
        subjectEntity.setDepartments(departmentEntity);
        subjectRepository.save(subjectEntity);
        return subjectConverter.toDTO(subjectEntity);
    }

    @Override
    public SubjectDTO update(SubjectDTO old_subjectDTO, SubjectInput subjectInput) {
        old_subjectDTO.setName(subjectInput.getName());
        SubjectEntity subjectEntityUpdate = subjectConverter.toEntity(old_subjectDTO);
        DepartmentEntity departmentEntity = departmentRepository.findOneByName(subjectInput.getNameDepartment());
        subjectEntityUpdate.setDepartments(departmentEntity);
        subjectRepository.save(subjectEntityUpdate);
        return subjectConverter.toDTO(subjectEntityUpdate);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            subjectRepository.deleteById(id);
        }
    }

    @Override
    public SubjectDTO findOneById(Long id) {
        SubjectEntity subjectEntity = subjectRepository.findOneById(id);
        return subjectEntity == null ? null : subjectConverter.toDTO(subjectEntity);
    }

    @Override
    public SubjectDTO findOneByName(String name) {
        SubjectEntity subjectEntity = subjectRepository.findOneByName(name);
        return subjectEntity == null ? null : subjectConverter.toDTO(subjectEntity);
    }

    @Override
    public List<SubjectDTO> showAll() {
        List<SubjectEntity> resultEntity = subjectRepository.findAll();
        List<SubjectDTO> resultDTO = new ArrayList<>();
        for (SubjectEntity subjectEntity : resultEntity) {
            SubjectDTO subjectDTO = subjectConverter.toDTO(subjectEntity);
            resultDTO.add(subjectDTO);
        }
        return resultDTO;
    }
}
