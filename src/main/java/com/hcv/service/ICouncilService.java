package com.hcv.service;

import com.hcv.dto.request.CouncilInput;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.CouncilResponse;
import com.hcv.dto.response.ShowAllResponse;

public interface ICouncilService {

    void insert(CouncilInput councilInput);

    void update(String oldCouncilId, CouncilInput councilInput);

    void delete(String... ids);

    CouncilResponse getDetail(String id);

    ShowAllResponse<CouncilResponse> showAll(ShowAllRequest showAllRequest);

}
