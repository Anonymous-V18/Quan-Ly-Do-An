package com.hcv.service;

import com.hcv.api_controller.input.SignInInput;
import com.hcv.api_controller.input.UpdateUserInput;
import com.hcv.dto.UserDTO;

public interface IAuthService {
    UserDTO createUser(SignInInput signinInput);

    UserDTO updateUser(UpdateUserInput updateUserInput);

}
