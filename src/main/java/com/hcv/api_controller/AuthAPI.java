package com.hcv.api_controller;

import com.hcv.api_controller.input.LogInInput;
import com.hcv.api_controller.input.SignInInput;
import com.hcv.api_controller.output.LoginOutput;
import com.hcv.dto.UserDTO;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> createdUser(@RequestBody SignInInput signInInput) {
        UserDTO userDTO = authService.createUser(signInInput);
        if (userDTO == null) {
            return new ResponseEntity<>("User already exists !", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(" SUCCESSFULLY !", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public LoginOutput createAuthenticationToken(@RequestBody LogInInput authInput) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authInput.getUsername(), authInput.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(authInput.getUsername());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        return new LoginOutput(token);
    }

}
