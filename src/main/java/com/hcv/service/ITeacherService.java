package com.hcv.service;

import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.TeacherInput;
import com.hcv.dto.request.TeacherInsertFromFileInput;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.TeacherDTO;
import com.hcv.dto.response.TeacherShowToSelectionResponse;

import java.util.List;

public interface ITeacherService {

    void checkDataBeforeInsert(TeacherInsertFromFileInput teacherInsertFromFileInput);

    List<TeacherDTO> insertFromFile(TeacherInsertFromFileInput teacherInsertFromFileInput);

    TeacherDTO insert(TeacherInput teacherInput);

    TeacherDTO update(String oldTeacherId, TeacherInput teacherInput);

    void delete(String[] ids);

    int countAll();

    ShowAllResponse<TeacherDTO> showAll(ShowAllRequest showAllRequest);

    List<TeacherShowToSelectionResponse> showAllToSelection();

    TeacherDTO findOneById(String id);

    List<TeacherDTO> findAll();

}
