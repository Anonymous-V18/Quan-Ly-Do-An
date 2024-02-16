package com.hcv.converter;

import com.hcv.dto.UserDTO;
import com.hcv.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    @Autowired
    private ModelMapper mapper;

    public UserDTO toDTO(UserEntity userEntity) {
        return mapper.map(userEntity, UserDTO.class);
    }

    public UserEntity toEntity(UserDTO userDTO) {
        return mapper.map(userDTO, UserEntity.class);
    }

}
