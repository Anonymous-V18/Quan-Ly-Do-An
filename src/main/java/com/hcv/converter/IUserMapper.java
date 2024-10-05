package com.hcv.converter;

import com.hcv.dto.request.UserRequest;
import com.hcv.dto.response.UserDTO;
import com.hcv.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper
public interface IUserMapper {

    UserDTO toDTO(User user);

    @Mapping(target = "password", ignore = true)
    UserDTO toShowDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userRequest.getPassword()))")
    User toEntity(UserRequest userRequest, PasswordEncoder passwordEncoder);

}
