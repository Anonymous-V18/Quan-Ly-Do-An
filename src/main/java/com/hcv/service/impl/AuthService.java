package com.hcv.service.impl;

import com.hcv.converter.IUserMapper;
import com.hcv.dto.UserDTO;
import com.hcv.dto.request.UpdateUserInput;
import com.hcv.dto.request.UserRequest;
import com.hcv.entity.RoleEntity;
import com.hcv.entity.UserEntity;
import com.hcv.repository.IRoleRepository;
import com.hcv.repository.IUserRepository;
import com.hcv.service.IAuthService;
import com.hcv.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IUserMapper userMapper;
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(UserRequest userRequest) {

        UserDTO checkUserNameExist = userService.findOneByUsername(userRequest.getUsername());
        if (checkUserNameExist != null) {
            return null;
        }

        UserEntity userEntity = userMapper.toEntity(userRequest, passwordEncoder);

        List<RoleEntity> listRolesEntity = userRequest.getNameRoles().stream().map(nameRole -> roleRepository.findOneByName(nameRole)).toList();

        userEntity.setRoles(listRolesEntity);

        userRepository.save(userEntity);

        return userMapper.toDTO(userEntity);
    }

    @Override
    public UserDTO updateUser(UpdateUserInput updateUserInput) {
        UserEntity userEntity = userRepository.findOneByUsername(updateUserInput.getUsername());
        String newPassword = passwordEncoder.encode(updateUserInput.getPassword());
        userEntity.setPassword(newPassword);
        userRepository.save(userEntity);
        return userMapper.toDTO(userEntity);
    }

    @Override
    public UserDTO updateUserForAdmin(UserRequest updateUserInput) {
        UserEntity userEntity = userMapper.toEntity(updateUserInput, passwordEncoder);
        List<RoleEntity> listRolesEntity = updateUserInput.getNameRoles().stream().map(nameRole -> roleRepository.findOneByName(nameRole)).toList();
        userEntity.setRoles(listRolesEntity);
        UserEntity userEntityOld = userRepository.findOneByUsername(updateUserInput.getUsername());
        userEntityOld = userMapper.toEntity(userEntityOld, userEntity);
        userRepository.save(userEntityOld);
        return userMapper.toDTO(userEntity);
    }

    @Override
    public void deleteUser(Long[] ids) {
        for (Long id : ids) {
            userRepository.deleteById(id);
        }
    }

}
