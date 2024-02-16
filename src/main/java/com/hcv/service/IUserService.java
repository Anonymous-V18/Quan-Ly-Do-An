package com.hcv.service;

import com.hcv.dto.UserDTO;

import java.util.List;

public interface IUserService {

    UserDTO findOneByUsername(String username);

    List<UserDTO> findAll();

}
