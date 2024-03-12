package com.hcv.service.impl;

import com.hcv.converter.DepartmentConverter;
import com.hcv.dto.DepartmentDTO;
import com.hcv.entity.DepartmentEntity;
import com.hcv.repository.IDepartmentRepository;
import com.hcv.repository.ISubjectRepository;
import com.hcv.service.IDepartmentService;
import com.hcv.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService implements IDepartmentService {

    @Autowired
    private IDepartmentRepository departmentRepository;

    @Autowired
    private DepartmentConverter departmentConverter;

    @Autowired
    private ISubjectRepository subjectRepository;

    @Autowired
    private ISubjectService subjectService;

    @Override
    public DepartmentDTO insert(DepartmentDTO departmentDTO) {
        DepartmentEntity departmentEntity = departmentConverter.toEntity(departmentDTO);
        departmentRepository.save(departmentEntity);
        return departmentDTO;
    }

    @Override
    public DepartmentDTO update(DepartmentDTO new_departmentDTO, DepartmentDTO old_departmentDTO) {
        old_departmentDTO.setName(new_departmentDTO.getName());
        DepartmentEntity departmentEntityUpdate = departmentConverter.toEntity(old_departmentDTO);
        departmentRepository.save(departmentEntityUpdate);
        return departmentConverter.toDTO(departmentEntityUpdate);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            departmentRepository.deleteById(id);
        }
    }

    @Override
    public DepartmentDTO findOneById(Long id) {
        DepartmentEntity departmentEntity = departmentRepository.findOneById(id);
        return departmentConverter.toDTO(departmentEntity);
    }

    @Override
    public DepartmentDTO findOneByName(String name) {
        DepartmentEntity departmentEntity = departmentRepository.findOneByName(name);
        return departmentEntity == null ? null : departmentConverter.toDTO(departmentEntity);
    }

    @Override
    public List<DepartmentDTO> showAll() {
        List<DepartmentEntity> resultEntity = departmentRepository.findAll();
        List<DepartmentDTO> resultDTO = new ArrayList<>();
        for (DepartmentEntity departmentEntity : resultEntity) {
            DepartmentDTO departmentDTO = departmentConverter.toDTO(departmentEntity);
            resultDTO.add(departmentDTO);
        }
        return resultDTO;
    }

}
