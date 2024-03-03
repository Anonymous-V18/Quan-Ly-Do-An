package com.hcv.api_controller;

import com.hcv.dto.UserDTO;
import com.hcv.dto.input.LogInInput;
import com.hcv.dto.input.UpdateUserInput;
import com.hcv.dto.input.UserRequest;
import com.hcv.dto.output.LoginOutput;
import com.hcv.service.IAuthService;
import com.hcv.service.impl.CustomUserDetailsService;
import com.hcv.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AuthAPI {

    @Autowired
    private IAuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> createdUser(@RequestBody UserRequest userRequest) {
        UserDTO userDTO = authService.createUser(userRequest);
        if (userDTO == null) {
            return new ResponseEntity<>("User already exists !", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(" SUCCESSFULLY !", HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LogInInput authInput) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authInput.getUsername(), authInput.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(authInput.getUsername());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        String allRole = userDetails.getAuthorities().toString();
        return new ResponseEntity<>(new LoginOutput(token, allRole), HttpStatus.OK);
    }

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserInput updateUserInput) {
        UserDTO userDTO = authService.updateUser(updateUserInput);
        return new ResponseEntity<>("Update Password successful", HttpStatus.OK);
    }

    @PutMapping("admin/update-user")
    public ResponseEntity<?> updateUserForAdmin(@RequestBody UserRequest updateUserInput) {
        UserDTO userDTO = authService.updateUserForAdmin(updateUserInput);
        return new ResponseEntity<>("Update user successful", HttpStatus.OK);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestBody Long[] ids) {
        authService.deleteUser(ids);
        return new ResponseEntity<>("Successfully !", HttpStatus.OK);
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "Not permissions";
    }

}
