package com.hcv.service;

import com.hcv.dto.SubjectDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.subject.SubjectInput;
import com.hcv.dto.response.ShowAllResponse;

import java.util.List;

public interface ISubjectService {

    SubjectDTO insert(SubjectInput subjectInput);

    SubjectDTO update(SubjectDTO oldSubjectDTO, SubjectInput subjectInput);

    void delete(String[] ids);

    SubjectDTO findOneById(String id);

    SubjectDTO findOneByName(String name);

    int countAll();

    ShowAllResponse<SubjectDTO> showAll(ShowAllRequest showAllRequest);

    List<SubjectDTO> findAll();
}
