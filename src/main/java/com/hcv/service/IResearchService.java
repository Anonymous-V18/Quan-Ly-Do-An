package com.hcv.service;

import com.hcv.dto.request.ResearchCancelRegistrationInput;
import com.hcv.dto.request.ResearchInput;
import com.hcv.dto.request.ResearchRegisterInput;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.ResearchDTO;
import com.hcv.dto.response.ResearchResponse;
import com.hcv.dto.response.ShowAllResponse;

public interface IResearchService {

    ResearchDTO insert(ResearchInput researchInput);

    ResearchDTO update(String oldResearchId, ResearchInput newResearchDTO);

    void delete(String[] ids);

    ResearchDTO findOneById(String id);

    int countAll();

    ShowAllResponse<ResearchResponse> showAll(ShowAllRequest showAllRequest);

    void registerResearch(ResearchRegisterInput researchRegisterInput);

    void cancelRegistrationResearch(ResearchCancelRegistrationInput researchCancelRegistrationInput);

}
