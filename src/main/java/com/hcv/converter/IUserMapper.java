package com.hcv.converter;

import com.hcv.dto.request.UserRequest;
import com.hcv.dto.response.UserDTO;
import com.hcv.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper
public interface IUserMapper {

    UserDTO toDTO(UserEntity userEntity);

    @Mapping(target = "password", ignore = true)
    UserDTO toShowDTO(UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userRequest.getPassword()))")
    UserEntity toEntity(UserRequest userRequest, PasswordEncoder passwordEncoder);

}
