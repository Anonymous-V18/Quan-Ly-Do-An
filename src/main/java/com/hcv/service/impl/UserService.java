package com.hcv.service.impl;

import com.hcv.converter.IUserMapper;
import com.hcv.dto.UserDTO;
import com.hcv.entity.UserEntity;
import com.hcv.repository.IUserRepository;
import com.hcv.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserMapper userMapper;
    @Autowired
    private IUserRepository userRepository;


    @Override
    public UserDTO findOneByUsername(String username) {
        UserEntity userEntity = userRepository.findOneByUsername(username);
        if (userEntity == null) {
            return null;
        }
        return userMapper.toDTO(userEntity);
    }

    @Override
    public List<UserDTO> findAll() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(userEntity -> userMapper.toDTO(userEntity)).toList();
    }


}
