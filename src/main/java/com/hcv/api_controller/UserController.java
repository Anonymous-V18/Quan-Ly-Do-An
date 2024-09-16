package com.hcv.api_controller;

import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.UserRequest;
import com.hcv.dto.request.UserUpdateInput;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.UserDTO;
import com.hcv.service.IUserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users")
public class UserController {

    IUserService userService;

    @PostMapping("/register")
    public ApiResponse<UserDTO> createdUser(@RequestBody @Valid UserRequest userRequest) {
        UserDTO response = userService.create(userRequest);
        return ApiResponse.<UserDTO>builder()
                .result(response)
                .build();
    }

    @PutMapping("/update-user")
    public ApiResponse<UserDTO> updateUser(@RequestBody @Valid UserUpdateInput updateUserInput) {
        UserDTO response = userService.update(updateUserInput);
        return ApiResponse.<UserDTO>builder()
                .result(response)
                .build();
    }

    @PutMapping("admin/update-user/{id}")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<UserDTO> updateUserForAdmin(@PathVariable(name = "id") String id,
                                                   @RequestBody @Valid UserRequest updateUserInput) {
        UserDTO response = userService.updateForAdmin(id, updateUserInput);
        return ApiResponse.<UserDTO>builder()
                .result(response)
                .build();
    }

    @DeleteMapping("/delete-user")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<String> deleteUser(@RequestBody String[] ids) {
        userService.delete(ids);
        return ApiResponse.<String>builder()
                .message("Xóa tài khoản thành công !")
                .build();
    }

    @GetMapping("/showAll")
    public ApiResponse<ShowAllResponse<UserDTO>> showAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(value = "orderBy", required = false, defaultValue = "id") String orderBy,
            @RequestParam(value = "orderDirection", required = false, defaultValue = "ASC") String orderDirection
    ) {
        ShowAllRequest showAllRequest = ShowAllRequest.builder()
                .currentPage(page)
                .limit(limit)
                .orderBy(orderBy)
                .orderDirection(orderDirection)
                .build();
        ShowAllResponse<UserDTO> response = userService.showAll(showAllRequest);
        return ApiResponse.<ShowAllResponse<UserDTO>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showAll-no-params")
    public ApiResponse<List<UserDTO>> getAll() {
        List<UserDTO> response = userService.findAll();
        return ApiResponse.<List<UserDTO>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/getMyInfo")
    public ApiResponse<UserDTO> getMyInfo() {
        String username = userService.getClaimsToken().get("username").toString();
        UserDTO response = userService.findOneByUsername(username);
        return ApiResponse.<UserDTO>builder()
                .result(response)
                .build();
    }

}
