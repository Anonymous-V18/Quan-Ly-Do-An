package com.hcv.api_controller;

import com.hcv.dto.UserDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserAPI {

    private final IUserService userService;

    @GetMapping("/showAll")
    @PreAuthorize("hasRole('HEAD_OF_DEPARTMENT')")
    public ResponseEntity<?> showListAllUsers(@RequestBody @Valid ShowAllRequest showAllRequest) {
        ShowAllResponse<?> response = userService.showAllUserResponse(showAllRequest);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(response).build(), HttpStatus.OK);
    }

    @GetMapping("/showOne")
    public ResponseEntity<?> showOneUser(@RequestParam(name = "userName") String userName) {
        UserDTO response = userService.findOneByUsername(userName);
        if (response == null) {
            return new ResponseEntity<>(ApiResponse.builder().code(10000).message("No search item !").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(response).build(), HttpStatus.OK);
    }

}
