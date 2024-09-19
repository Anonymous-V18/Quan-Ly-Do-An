package com.hcv.service;

import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.DepartmentDTO;
import com.hcv.dto.response.ShowAllResponse;

import java.util.List;

public interface IDepartmentService {

    DepartmentDTO insert(DepartmentDTO departmentDTO);

    DepartmentDTO update(String oldDepartmentId, DepartmentDTO newDepartmentDTO);

    void delete(String[] ids);

    int count();

    DepartmentDTO findOneByName(String name);

    ShowAllResponse<DepartmentDTO> showAll(ShowAllRequest showAllRequest);

    List<DepartmentDTO> findAll();

}
