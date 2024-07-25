package com.hcv.service;

import com.hcv.dto.StudentDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.StudentFromExcelInput;
import com.hcv.dto.request.StudentInput;
import com.hcv.dto.response.ShowAllResponse;

import java.util.List;

public interface IStudentService {

    void checkDataBeforeInsert(StudentFromExcelInput studentFromExcelInput);

    StudentDTO insert(StudentInput studentInput);

    StudentDTO update(StudentDTO oldStudentDTO, StudentInput studentInput);

    void delete(String[] ids);

    StudentDTO findOneByMaSo(String maSo);

    StudentDTO findOneById(String id);

    int countAll();

    ShowAllResponse<StudentDTO> showAll(ShowAllRequest showAllRequest);

    List<StudentDTO> findAll();

    List<StudentDTO> findAllById(List<String> ids);
}
