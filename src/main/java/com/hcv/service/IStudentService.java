package com.hcv.service;

import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.StudentFromExcelInput;
import com.hcv.dto.request.StudentInput;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.StudentDTO;

import java.util.List;

public interface IStudentService {

    void checkDataBeforeInsert(StudentFromExcelInput studentFromExcelInput);

    StudentDTO insert(StudentInput studentInput);

    StudentDTO update(String oldStudentId, StudentInput studentInput);

    void delete(String[] ids);

    StudentDTO findOneByMaSo(String maSo);

    StudentDTO findOneById(String id);

    int countAll();

    ShowAllResponse<StudentDTO> showAll(ShowAllRequest showAllRequest);

    List<StudentDTO> findAll();

}
