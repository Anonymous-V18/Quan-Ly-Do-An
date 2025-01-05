package com.hcv.service;

import com.hcv.dto.request.SystemVariableInput;
import com.hcv.dto.response.SystemVariableResponse;

import java.util.List;

public interface ISystemVariableService {

    SystemVariableResponse update(String systemVariableId, SystemVariableInput systemVariableInput);

    List<SystemVariableResponse> showAll();

}
