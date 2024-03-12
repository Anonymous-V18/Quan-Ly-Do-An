package com.hcv.service;

import com.hcv.dto.UserDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.ShowAllResponse;

import java.util.List;

public interface IUserService {

    UserDTO findOneByUsername(String username);

    List<UserDTO> findAll();

    int countAll();

    ShowAllResponse<UserDTO> showAllUserResponse(ShowAllRequest showAllRequest);


}
