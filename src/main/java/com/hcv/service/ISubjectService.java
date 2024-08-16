package com.hcv.service;

import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.SubjectInput;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.SubjectDTO;
import com.hcv.dto.response.SubjectResponse;

import java.util.List;

public interface ISubjectService {

    SubjectDTO insert(SubjectInput subjectInput);

    SubjectDTO update(String oldSubjectId, SubjectInput subjectInput);

    void delete(String[] ids);

    SubjectDTO findOneById(String id);

    SubjectDTO findOneByName(String name);

    int countAll();

    ShowAllResponse<SubjectDTO> showAll(ShowAllRequest showAllRequest);

    List<SubjectDTO> findAll();

    List<SubjectResponse> showAllByDepartment(String departmentId);
}
