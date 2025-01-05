package com.hcv.service.impl;

import com.hcv.converter.IUserMapper;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.UserRequest;
import com.hcv.dto.request.UserUpdateInput;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.UserDTO;
import com.hcv.entity.Role;
import com.hcv.entity.User;
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
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.*;

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
        boolean isUserExisted = userRepository.existsByUsername(userRequest.getUsername());
        if (isUserExisted) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toEntity(userRequest, passwordEncoder);

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(userRequest.getRoleIds()));
        if (roles.size() != userRequest.getRoleIds().size()) {
            throw new AppException(ErrorCode.INVALID_ROLE);
        }
        user.setRoles(roles);

        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO update(UserUpdateInput updateUserInput) {
        String currentUserId = this.getClaimsToken().get("sub").toString();

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String newPassword = passwordEncoder.encode(updateUserInput.getPassword());
        user.setPassword(newPassword);
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO updateForAdmin(String oldUserId, UserRequest updateUserInput) {
        User userOld = userRepository.findById(oldUserId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userOld.setPassword(passwordEncoder.encode(updateUserInput.getPassword()));
        userOld.setIsActivated(updateUserInput.getIsActivated());
        userOld = this.updateRoles(userOld, updateUserInput.getRoleIds());

        return userMapper.toDTO(userOld);
    }

    @Override
    public User updateRoles(User user, Set<String> roleIds) {
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(roleIds));
        if (roles.size() != roleIds.size()) {
            throw new AppException(ErrorCode.INVALID_ROLE);
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public UserDTO findOneByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toDTO(user);
    }

    @Override
    public Map<String, Object> getClaimsToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> currentClaimsToken = new HashMap<>();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentClaimsToken = ((JwtAuthenticationToken) authentication).getToken().getClaims();
        }
        return currentClaimsToken;
    }

    @Override
    public int count() {
        return (int) userRepository.count();
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toShowDTO).toList();
    }

    @Override
    public ShowAllResponse<UserDTO> showAll(ShowAllRequest showAllRequest) {
        int page = showAllRequest.getCurrentPage();
        int limit = showAllRequest.getLimit();
        Pageable paging = PageRequest.of(
                page - 1,
                limit,
                Sort.by(Sort.Direction.fromString(showAllRequest.getOrderDirection()), showAllRequest.getOrderBy())
        );
        Page<User> userEntityList = userRepository.findAll(paging);
        List<UserDTO> resultDTO = userEntityList.getContent().stream().map(userMapper::toShowDTO).toList();

        int totalElements = (int) userRepository.count();
        int totalPages = (int) Math.ceil((1.0 * totalElements) / limit);

        return ShowAllResponse.<UserDTO>builder()
                .currentPage(page)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .responses(resultDTO)
                .build();
    }


}
