package com.hcv.converter;

import com.hcv.dto.UserDTO;
import com.hcv.dto.request.user.UserRequest;
import com.hcv.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper
public interface IUserMapper {

    UserDTO toDTO(UserEntity userEntity);

    @Mapping(target = "password", ignore = true)
    UserDTO toShowDTO(UserEntity userEntity);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userRequest.getPassword()))")
    @Mapping(target = "roles", ignore = true)
    UserEntity toEntity(UserRequest userRequest, PasswordEncoder passwordEncoder);

}
