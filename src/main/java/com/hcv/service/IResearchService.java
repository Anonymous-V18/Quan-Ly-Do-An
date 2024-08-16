package com.hcv.service;

import com.hcv.dto.request.*;
import com.hcv.dto.response.ResearchDTO;
import com.hcv.dto.response.ResearchResponse;
import com.hcv.dto.response.ResearchShowToRegistrationResponse;
import com.hcv.dto.response.ShowAllResponse;

import java.util.List;

public interface IResearchService {

    void checkDataBeforeInsert(ResearchInsertFromFileInput researchInsertFromFileInput);

    List<ResearchDTO> insertFromFile(ResearchInsertFromFileInput researchInsertFromFileInput);

    ResearchDTO insert(ResearchInput researchInput);

    ResearchDTO update(String oldResearchId, ResearchUpdateInput newResearchUpdateInput);

    void delete(String[] ids);

    ResearchDTO findOneById(String id);

    ResearchResponse showDetail(String id);

    int countAll();

    ShowAllResponse<ResearchResponse> showAll(ShowAllRequest showAllRequest);

    void registerResearch(ResearchRegisterInput researchRegisterInput);

    void cancelRegistrationResearch(ResearchCancelRegistrationInput researchCancelRegistrationInput);

    ResearchDTO markApproved(String id);

    ShowAllResponse<ResearchShowToRegistrationResponse> showAllToRegistration(ShowAllRequest showAllRequest);
}
