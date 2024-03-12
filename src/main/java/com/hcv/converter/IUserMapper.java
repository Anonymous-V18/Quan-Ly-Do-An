package com.hcv.converter;

import com.hcv.dto.UserDTO;
import com.hcv.dto.request.UserRequest;
import com.hcv.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper
public interface IUserMapper {

    UserDTO toDTO(UserEntity userEntity);

    UserEntity toEntity(UserDTO userDTO);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userRequest.getPassword()))")
    @Mapping(target = "roles", ignore = true)
    UserEntity toEntity(UserRequest userRequest, PasswordEncoder passwordEncoder);

    UserEntity toEntity(@MappingTarget UserEntity oldEntity, UserEntity newEntity);

}
