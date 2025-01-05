package com.hcv.service;

import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.UserRequest;
import com.hcv.dto.request.UserUpdateInput;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.UserDTO;
import com.hcv.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IUserService {

    UserDTO create(UserRequest userRequest);

    UserDTO updateForAdmin(String oldUserId, UserRequest updateUserInput);

    User updateRoles(User user, Set<String> roleIds);

    UserDTO update(UserUpdateInput updateUserInput);

    void delete(String[] ids);

    UserDTO findOneByUsername(String username);

    Map<String, Object> getClaimsToken();

    int count();

    List<UserDTO> findAll();

    ShowAllResponse<UserDTO> showAll(ShowAllRequest showAllRequest);

}
