package com.hcv.api_controller;

import com.hcv.dto.RoleDTO;
import com.hcv.dto.UserDTO;
import com.hcv.dto.request.LogInInput;
import com.hcv.dto.request.UpdateUserInput;
import com.hcv.dto.request.UserRequest;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.LoginOutput;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.service.IAuthService;
import com.hcv.service.IUserService;
import com.hcv.service.impl.CustomUserDetailsService;
import com.hcv.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class AuthAPI {

    private final IAuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createdUser(@RequestBody @Valid UserRequest userRequest) {
        authService.createUser(userRequest);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).message("Successfully !").build(), HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Valid LogInInput authInput) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authInput.getUsername(), authInput.getPassword()));
        } catch (UsernameNotFoundException | BadCredentialsException u) {
            throw new AppException(ErrorCode.INVALID_USERNAME_OR_PASSWORD);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authInput.getUsername());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        UserDTO userDTO = userService.findOneByUsername(userDetails.getUsername());
        if (userDetails.getAuthorities().toString().contains("ROLE_STUDENT")) {
            LoginOutput loginOutput = new LoginOutput(token, userDTO.getId(), List.of("ROLE_STUDENT"), userDTO.getStudents());
            return new ResponseEntity<>(ApiResponse.builder().code(10000).result(loginOutput).build(), HttpStatus.OK);
        }
        List<String> authorities = userDTO.getRoles().stream().map(RoleDTO::getName).toList();
        LoginOutput loginOutput = new LoginOutput(token, userDTO.getId(), authorities, userDTO.getTeachers());
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(loginOutput).build(), HttpStatus.OK);
    }

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UpdateUserInput updateUserInput) {
        authService.updateUser(updateUserInput);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).message("Update Successful !").build(), HttpStatus.OK);
    }

    @PutMapping("admin/update-user")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ResponseEntity<?> updateUserForAdmin(@RequestBody @Valid UserRequest updateUserInput) {
        authService.updateUserForAdmin(updateUserInput);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).message("Update Successful !").build(), HttpStatus.OK);
    }

    @DeleteMapping("/delete-user")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ResponseEntity<?> deleteUser(@RequestBody Long[] ids) {
        authService.deleteUser(ids);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).message("Deleted Successfully !").build(), HttpStatus.OK);
    }


}
