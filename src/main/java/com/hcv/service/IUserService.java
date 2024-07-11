package com.hcv.service;

import com.hcv.dto.UserDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.UpdateUserInput;
import com.hcv.dto.request.UserRequest;
import com.hcv.dto.response.ShowAllResponse;

import java.util.List;

public interface IUserService {

    UserDTO createUser(UserRequest userRequest);

    UserDTO updateUserForAdmin(UserRequest updateUserInput);

    UserDTO updateUser(UpdateUserInput updateUserInput);

    void deleteUser(String[] ids);

    UserDTO findOneByUsername(String username);

    List<UserDTO> findAll();

    int countAll();

    ShowAllResponse<UserDTO> showAllUserResponse(ShowAllRequest showAllRequest);


}
