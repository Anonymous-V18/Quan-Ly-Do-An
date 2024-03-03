package com.hcv.service.impl;

import com.hcv.converter.IDepartmentMapper;
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
    private IDepartmentMapper departmentMapper;

    @Autowired
    private ISubjectRepository subjectRepository;

    @Autowired
    private ISubjectService subjectService;

    @Override
    public void insert(DepartmentDTO departmentDTO) {
        DepartmentEntity departmentEntity = departmentMapper.toEntity(departmentDTO);
        departmentRepository.save(departmentEntity);
    }

    @Override
    public void update(DepartmentDTO new_departmentDTO, DepartmentDTO old_departmentDTO) {
        old_departmentDTO.setName(new_departmentDTO.getName());
        DepartmentEntity departmentEntityUpdate = departmentMapper.toEntity(old_departmentDTO);
        departmentRepository.save(departmentEntityUpdate);
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
        return departmentMapper.toDTO(departmentEntity);
    }

    @Override
    public DepartmentDTO findOneByName(String name) {
        DepartmentEntity departmentEntity = departmentRepository.findOneByName(name);
        return departmentEntity == null ? null : departmentMapper.toDTO(departmentEntity);
    }

    @Override
    public List<DepartmentDTO> showAll() {
        List<DepartmentEntity> resultEntity = departmentRepository.findAll();
        List<DepartmentDTO> resultDTO = new ArrayList<>();
        for (DepartmentEntity departmentEntity : resultEntity) {
            DepartmentDTO departmentDTO = departmentMapper.toDTO(departmentEntity);
            resultDTO.add(departmentDTO);
        }
        return resultDTO;
    }

}
