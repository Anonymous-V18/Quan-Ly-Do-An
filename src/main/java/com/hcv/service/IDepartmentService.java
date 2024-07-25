package com.hcv.service;

import com.hcv.dto.DepartmentDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.ShowAllResponse;

import java.util.List;

public interface IDepartmentService {

    DepartmentDTO insert(DepartmentDTO departmentDTO);

    DepartmentDTO update(DepartmentDTO newDepartmentDTO, DepartmentDTO oldDepartmentDTO);

    void delete(String[] ids);

    DepartmentDTO findOneById(String id);

    DepartmentDTO findOneByName(String name);

    int countAll();

    ShowAllResponse<DepartmentDTO> showAll(ShowAllRequest showAllRequest);

    List<DepartmentDTO> findAll();
}
