package com.hcv.service;

import com.hcv.dto.SubjectDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.SubjectInput;
import com.hcv.dto.response.ShowAllResponse;

public interface ISubjectService {

    SubjectDTO insert(SubjectInput subjectInput);

    SubjectDTO update(SubjectDTO old_subjectDTO, SubjectInput subjectInput);

    void delete(Long[] ids);

    SubjectDTO findOneById(Long id);

    SubjectDTO findOneByName(String name);

    int countAll();

    ShowAllResponse<SubjectDTO> showAll(ShowAllRequest showAllRequest);
}
