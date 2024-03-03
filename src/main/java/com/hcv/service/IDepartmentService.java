package com.hcv.service;

import com.hcv.dto.DepartmentDTO;

import java.util.List;

public interface IDepartmentService {

    DepartmentDTO insert(DepartmentDTO departmentDTO);

    DepartmentDTO update(DepartmentDTO new_departmentDTO, DepartmentDTO old_departmentDTO);

    void delete(Long[] ids);

    DepartmentDTO findOneById(Long id);

    DepartmentDTO findOneByName(String name);

    List<DepartmentDTO> showAll();
}
