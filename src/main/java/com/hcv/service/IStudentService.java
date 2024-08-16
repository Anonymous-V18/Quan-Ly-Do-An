package com.hcv.service;

import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.StudentInput;
import com.hcv.dto.request.StudentInsertFromFileInput;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.StudentDTO;
import com.hcv.dto.response.StudentShowToSelectionResponse;

import java.util.List;

public interface IStudentService {

    void checkDataBeforeInsert(StudentInsertFromFileInput studentInsertFromFileInput);

    List<StudentDTO> insertFromFile(StudentInsertFromFileInput studentInsertFromFileInput);

    StudentDTO insert(StudentInput studentInput);

    StudentDTO update(String oldStudentId, StudentInput studentInput);

    void delete(String[] ids);

    StudentDTO findOneById(String id);

    int countAll();

    ShowAllResponse<StudentDTO> showAll(ShowAllRequest showAllRequest);

    List<StudentShowToSelectionResponse> showAllToSelection();

    List<StudentDTO> findAll();

}
