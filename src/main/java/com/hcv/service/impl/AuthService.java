package com.hcv.service.impl;

import com.hcv.api_controller.input.SignInInput;
import com.hcv.converter.UserConverter;
import com.hcv.dto.UserDTO;
import com.hcv.entity.RoleEntity;
import com.hcv.entity.UserEntity;
import com.hcv.repository.IRoleRepository;
import com.hcv.repository.IUserRepository;
import com.hcv.service.IAuthService;
import com.hcv.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(SignInInput signinInput) {

        UserDTO checkUserNameExist = userService.findOneByUsername(signinInput.getUsername());
        if (checkUserNameExist != null) {
            return null;
        }

        String username = signinInput.getUsername();
        String password = passwordEncoder.encode(signinInput.getPassword());
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setIsGraduate(1);
        UserEntity userEntity = userConverter.toEntity(userDTO);

        List<RoleEntity> listRolesEntity = new ArrayList<>();
        int sizeListNameRoles = signinInput.getNameRoles().size();
        RoleEntity roleEntity = new RoleEntity();
        for (int i = 0; i < sizeListNameRoles; i++) {
            String nameRole = signinInput.getNameRoles().get(i).trim();
            roleEntity = roleRepository.findOneByName(nameRole);
            listRolesEntity.add(roleEntity);
        }
        userEntity.setRoles(listRolesEntity);

        userRepository.save(userEntity);

        return userConverter.toDTO(userEntity);
    }

}
