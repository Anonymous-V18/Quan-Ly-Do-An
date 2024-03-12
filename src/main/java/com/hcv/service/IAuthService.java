package com.hcv.service;

import com.hcv.dto.UserDTO;
import com.hcv.dto.request.UpdateUserInput;
import com.hcv.dto.request.UserRequest;

public interface IAuthService {
    UserDTO createUser(UserRequest userRequest);

    UserDTO updateUserForAdmin(UserRequest updateUserInput);

    UserDTO updateUser(UpdateUserInput updateUserInput);

    void deleteUser(Long[] ids);

}
