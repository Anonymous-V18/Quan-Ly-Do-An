package com.hcv.api_controller;

import com.hcv.dto.UserDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.UpdateUserInput;
import com.hcv.dto.request.UserRequest;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.service.IUserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users")
public class UserAPI {

    IUserService userService;

    @PostMapping("/register")
    public ApiResponse<String> createdUser(@RequestBody @Valid UserRequest userRequest) {
        userService.createUser(userRequest);
        return ApiResponse.<String>builder()
                .message("Tạo tài khoản thành công !")
                .build();
    }

    @PutMapping("/update-user")
    public ApiResponse<String> updateUser(@RequestBody @Valid UpdateUserInput updateUserInput) {
        userService.updateUser(updateUserInput);
        return ApiResponse.<String>builder()
                .message("Cập nhật tài khoản thành công !")
                .build();
    }

    @PutMapping("admin/update-user")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<String> updateUserForAdmin(@RequestBody @Valid UserRequest updateUserInput) {
        userService.updateUserForAdmin(updateUserInput);
        return ApiResponse.<String>builder()
                .message("Cập nhật tài khoản thành công !")
                .build();
    }

    @DeleteMapping("/delete-user")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<String> deleteUser(@RequestBody String[] ids) {
        userService.deleteUser(ids);
        return ApiResponse.<String>builder()
                .message("Xóa tài khoản thành công !")
                .build();
    }

    @GetMapping("/showAll")
    public ApiResponse<ShowAllResponse<UserDTO>> showAll(@RequestParam(name = "page") Integer page,
                                                         @RequestParam(name = "limit") Integer limit,
                                                         @RequestParam(name = "orderBy") String orderBy,
                                                         @RequestParam(name = "orderDirection") String orderDirection) {
        ShowAllRequest showAllRequest = ShowAllRequest.builder()
                .page(page)
                .limit(limit)
                .orderBy(orderBy)
                .orderDirection(orderDirection)
                .build();
        ShowAllResponse<UserDTO> response = userService.showAllUserResponse(showAllRequest);
        return ApiResponse.<ShowAllResponse<UserDTO>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showAll-no-params")
    public ApiResponse<List<UserDTO>> showAll() {
        List<UserDTO> response = userService.findAll();
        return ApiResponse.<List<UserDTO>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showOne")
    public ApiResponse<UserDTO> showOne(@RequestParam(name = "userName") String userName) {
        UserDTO response = userService.findOneByUsername(userName);
        return ApiResponse.<UserDTO>builder()
                .result(response)
                .build();
    }

}
