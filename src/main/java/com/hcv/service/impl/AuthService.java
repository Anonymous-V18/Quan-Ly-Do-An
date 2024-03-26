package com.hcv.service.impl;

import com.hcv.converter.IUserMapper;
import com.hcv.dto.UserDTO;
import com.hcv.dto.request.UpdateUserInput;
import com.hcv.dto.request.UserRequest;
import com.hcv.entity.RoleEntity;
import com.hcv.entity.UserEntity;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IRoleRepository;
import com.hcv.repository.IUserRepository;
import com.hcv.service.IAuthService;
import com.hcv.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService implements IAuthService {

    IUserService userService;
    IRoleRepository roleRepository;
    IUserMapper userMapper;
    IUserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(UserRequest userRequest) {
        UserDTO checkUserNameExist = userService.findOneByUsername(userRequest.getUsername());
        if (checkUserNameExist != null) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        UserEntity userEntity = userMapper.toEntity(userRequest, passwordEncoder);
        List<RoleEntity> listRolesEntity = userRequest.getNameRoles().stream().map(roleRepository::findOneByName).toList();
        if (listRolesEntity.getFirst() == null) {
            throw new AppException(ErrorCode.INVALID_NAME_ROLE);
        }
        userEntity.setRoles(listRolesEntity);
        userRepository.save(userEntity);
        return userMapper.toDTO(userEntity);
    }

    @Override
    public UserDTO updateUser(UpdateUserInput updateUserInput) {
        UserEntity userEntity = userRepository.findOneByUsername(updateUserInput.getUsername());
        if (userEntity == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        String newPassword = passwordEncoder.encode(updateUserInput.getPassword());
        userEntity.setPassword(newPassword);
        userRepository.save(userEntity);
        return userMapper.toDTO(userEntity);
    }

    @Override
    public UserDTO updateUserForAdmin(UserRequest updateUserInput) {
        List<RoleEntity> listRolesEntity = updateUserInput.getNameRoles().stream()
                .map(roleRepository::findOneByName)
                .collect(Collectors.toList());
        if (listRolesEntity.getFirst() == null) {
            throw new AppException(ErrorCode.INVALID_NAME_ROLE);
        }
        UserEntity userEntityOld = userRepository.findOneByUsername(updateUserInput.getUsername());
        if (userEntityOld == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        userEntityOld.setRoles(listRolesEntity);
        userEntityOld.setPassword(passwordEncoder.encode(updateUserInput.getPassword()));
        userEntityOld.setIsGraduate(updateUserInput.getIsGraduate());
        userRepository.save(userEntityOld);
        return userMapper.toDTO(userEntityOld);
    }

    @Override
    public void deleteUser(Long[] ids) {
        for (Long id : ids) {
            userRepository.deleteById(id);
        }
    }

}
