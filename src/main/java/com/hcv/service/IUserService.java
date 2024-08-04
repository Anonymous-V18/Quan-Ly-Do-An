package com.hcv.service;

import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.UserRequest;
import com.hcv.dto.request.UserUpdateInput;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.UserDTO;

import java.util.List;

public interface IUserService {

    UserDTO create(UserRequest userRequest);

    UserDTO updateForAdmin(UserRequest updateUserInput);

    UserDTO update(String oldUserId, UserUpdateInput updateUserInput);

    void delete(String[] ids);

    UserDTO findOneByUsername(String username);

    String getSubToken();

    List<UserDTO> findAll();

    int countAll();

    ShowAllResponse<UserDTO> showAll(ShowAllRequest showAllRequest);

}
