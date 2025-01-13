package com.hcv.service;

import com.hcv.dto.StatusResearch;
import com.hcv.dto.request.*;
import com.hcv.dto.response.ResearchDTO;
import com.hcv.dto.response.ResearchResponse;
import com.hcv.dto.response.ResearchShowToRegistrationResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.entity.Teacher;
import com.hcv.entity.TypeTeacher;

import java.util.Collection;
import java.util.List;

public interface IResearchService {

    List<ResearchDTO> insertFromFile(ResearchInsertFromFileInput researchInsertFromFileInput);

    ResearchDTO insert(ResearchInput researchInput, Teacher creator, TypeTeacher typeTeacher);

    ResearchDTO update(String oldResearchId, ResearchUpdateInput newResearchUpdateInput);

    void delete(String[] ids);

    ResearchDTO findOneById(String id);

    ResearchResponse showDetail(String id);

    void registerResearch(ResearchRegistrationInput researchRegistrationInput);

    void cancelRegistrationResearch(ResearchCancelRegistrationInput researchCancelRegistrationInput);

    int countByStatusInAndSubjectsIdAndStageAndSchoolYear(Collection<StatusResearch> statuses, String id,
                                                          String stage, String schoolYear);

    int countByCurrentResearch(String teacherId, String typeTeacherCode,
                               String schoolYear, String stage, String status);

    ShowAllResponse<ResearchResponse> showAllMyResearch(ShowAllRequest showAllRequest, boolean isRoleCouncil,
                                                        boolean isRoleThesisAdvisor, boolean isRoleInstructor,
                                                        String status);

    ShowAllResponse<ResearchResponse> showAllToFeedback(ShowAllRequest showAllRequest);

    ShowAllResponse<ResearchShowToRegistrationResponse> showAllToRegistration(ShowAllRequest showAllRequest);

    ShowAllResponse<ResearchShowToRegistrationResponse> showAllToApprovalProcessing(ShowAllRequest showAllRequest);

    ShowAllResponse<ResearchResponse> searchCriteria(Integer page, Integer limit, String sortBy, String... search);

    ResearchDTO markApproved(String id);

    ResearchDTO cancelApproval(String id);

    void updateThesisAdvisor(String researchId, String thesisAdvisorId);

    void broughtToTheCouncil(String researchId);
}
