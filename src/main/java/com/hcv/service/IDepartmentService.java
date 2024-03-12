package com.hcv.service;

import com.hcv.dto.DepartmentDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.ShowAllResponse;

public interface IDepartmentService {

    DepartmentDTO insert(DepartmentDTO departmentDTO);

    DepartmentDTO update(DepartmentDTO new_departmentDTO, DepartmentDTO old_departmentDTO);

    void delete(Long[] ids);

    DepartmentDTO findOneById(Long id);

    DepartmentDTO findOneByName(String name);

    int countAll();

    ShowAllResponse<DepartmentDTO> showAll(ShowAllRequest showAllRequest);
}
