package com.hcv.service.impl;

import com.hcv.converter.IUserMapper;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.UserRequest;
import com.hcv.dto.request.UserUpdateInput;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.UserDTO;
import com.hcv.entity.RoleEntity;
import com.hcv.entity.UserEntity;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IRoleRepository;
import com.hcv.repository.IUserRepository;
import com.hcv.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {

    IRoleRepository roleRepository;
    IUserMapper userMapper;
    IUserRepository userRepository;
    PasswordEncoder passwordEncoder;


    @Override
    public UserDTO create(UserRequest userRequest) {
        UserDTO checkUserNameExist = this.findOneByUsername(userRequest.getUsername());
        if (checkUserNameExist != null) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        UserEntity userEntity = userMapper.toEntity(userRequest, passwordEncoder);
        List<RoleEntity> listRolesEntity = userRequest.getNameRoles().stream()
                .map(roleRepository::findOneByName)
                .toList();
        if (listRolesEntity.contains(null)) {
            throw new AppException(ErrorCode.INVALID_NAME_ROLE);
        }
        userEntity.setRoles(listRolesEntity);
        userRepository.save(userEntity);
        return userMapper.toDTO(userEntity);
    }

    @Override
    public UserDTO update(String oldUserId, UserUpdateInput updateUserInput) {
        UserEntity userEntity = userRepository.findOneById(oldUserId);
        if (userEntity == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        String newPassword = passwordEncoder.encode(updateUserInput.getPassword());
        userEntity.setPassword(newPassword);
        userRepository.save(userEntity);
        return userMapper.toDTO(userEntity);
    }

    @Override
    public UserDTO updateForAdmin(UserRequest updateUserInput) {
        List<RoleEntity> listRolesEntity = updateUserInput.getNameRoles().stream()
                .map(roleRepository::findOneByName)
                .toList();
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
    public void delete(String[] ids) {
        for (String id : ids) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public UserDTO findOneByUsername(String username) {
        UserEntity userEntity = userRepository.findOneByUsername(username);
        if (userEntity == null) {
            return null;
        }
        return userMapper.toDTO(userEntity);
    }

    @Override
    public String getSubToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = " ";
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        return currentUserName;
    }

    @Override
    public List<UserDTO> findAll() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(userMapper::toShowDTO).toList();
    }

    @Override
    public int countAll() {
        return (int) userRepository.count();
    }

    @Override
    public ShowAllResponse<UserDTO> showAll(ShowAllRequest showAllRequest) {
        int page = showAllRequest.getPage();
        int limit = showAllRequest.getLimit();
        int totalPages = (int) Math.ceil((1.0 * countAll()) / limit);

        Pageable paging = PageRequest.of(
                page - 1,
                limit,
                Sort.by(Sort.Direction.fromString(showAllRequest.getOrderDirection()), showAllRequest.getOrderBy())
        );
        Page<UserEntity> userEntityList = userRepository.findAll(paging);
        List<UserEntity> resultEntity = userEntityList.getContent();
        List<UserDTO> resultDTO = resultEntity.stream().map(userMapper::toShowDTO).toList();

        return ShowAllResponse.<UserDTO>builder()
                .page(page)
                .totalPages(totalPages)
                .responses(resultDTO)
                .build();
    }


}
