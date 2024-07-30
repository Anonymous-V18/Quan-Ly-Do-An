package com.hcv.service;

import com.hcv.dto.ResearchDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.research.CancelRegistrationResearchInput;
import com.hcv.dto.request.research.RegisterResearchInput;
import com.hcv.dto.request.research.ResearchInput;
import com.hcv.dto.response.ResearchResponse;
import com.hcv.dto.response.ShowAllResponse;

public interface IResearchService {

    ResearchDTO insert(ResearchInput researchInput);

    ResearchDTO update(String oldResearchId, ResearchInput newResearchDTO);

    void delete(String[] ids);

    ResearchDTO findOneById(String id);

    int countAll();

    ShowAllResponse<ResearchResponse> showAll(ShowAllRequest showAllRequest);

    void registerResearch(RegisterResearchInput registerResearchInput);

    void cancelRegistrationResearch(CancelRegistrationResearchInput cancelRegistrationResearchInput);
    
}
