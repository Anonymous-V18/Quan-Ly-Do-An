package com.hcv.service;

import com.hcv.dto.request.*;
import com.hcv.dto.response.ResearchDTO;
import com.hcv.dto.response.ResearchResponse;
import com.hcv.dto.response.ResearchShowToRegistrationResponse;
import com.hcv.dto.response.ShowAllResponse;

import java.util.List;

public interface IResearchService {

    List<ResearchDTO> insertFromFile(ResearchInsertFromFileInput researchInsertFromFileInput);

    ResearchDTO insert(ResearchInput researchInput);

    ResearchDTO update(String oldResearchId, ResearchUpdateInput newResearchUpdateInput);

    void delete(String[] ids);

    ResearchDTO findOneById(String id);

    ResearchResponse showDetail(String id);

    void registerResearch(ResearchRegisterInput researchRegisterInput);

    void cancelRegistrationResearch(ResearchCancelRegistrationInput researchCancelRegistrationInput);

    ShowAllResponse<ResearchResponse> showAllToFeedback(ShowAllRequest showAllRequest);

    ShowAllResponse<ResearchShowToRegistrationResponse> showAllToRegistration(ShowAllRequest showAllRequest);

    ShowAllResponse<ResearchShowToRegistrationResponse> showAllToApprovalProcessing(ShowAllRequest showAllRequest);

    ResearchDTO markApproved(String id);

    ResearchDTO cancelApproval(String id);
}
