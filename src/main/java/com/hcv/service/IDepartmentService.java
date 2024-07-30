package com.hcv.service;

import com.hcv.dto.DepartmentDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.ShowAllResponse;

import java.util.List;

public interface IDepartmentService {

    DepartmentDTO insert(DepartmentDTO departmentDTO);

    DepartmentDTO update(String oldDepartmentId, DepartmentDTO newDepartmentDTO);

    void delete(String[] ids);

    DepartmentDTO findOneByName(String name);

    int countAll();

    ShowAllResponse<DepartmentDTO> showAll(ShowAllRequest showAllRequest);

    List<DepartmentDTO> findAll();

}
