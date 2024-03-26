package com.hcv.service.impl;

import com.hcv.converter.IUserMapper;
import com.hcv.dto.UserDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.entity.UserEntity;
import com.hcv.repository.IUserRepository;
import com.hcv.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {

    IUserMapper userMapper;
    IUserRepository userRepository;


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
        return users.stream().map(userMapper::toShowDTO).toList();
    }

    @Override
    public int countAll() {
        return (int) userRepository.count();
    }

    @Override
    public ShowAllResponse<UserDTO> showAllUserResponse(ShowAllRequest showAllRequest) {
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
