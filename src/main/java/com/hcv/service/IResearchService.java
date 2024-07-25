package com.hcv.service;

import com.hcv.dto.ResearchDTO;
import com.hcv.dto.request.CancelRegistrationResearchInput;
import com.hcv.dto.request.RegisterResearchInput;
import com.hcv.dto.request.ResearchInput;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.ResearchResponse;
import com.hcv.dto.response.ShowAllResponse;

public interface IResearchService {

    ResearchDTO insert(ResearchInput researchInput);

    ResearchDTO update(ResearchDTO oldResearchDTO, ResearchInput newResearchDTO);

    void delete(String[] ids);

    ResearchDTO findOneById(String id);

    int countAll();

    ShowAllResponse<ResearchResponse> showAll(ShowAllRequest showAllRequest);

    void registerResearch(RegisterResearchInput registerResearchInput);

    void cancelRegistrationResearch(CancelRegistrationResearchInput cancelRegistrationResearchInput);
}
