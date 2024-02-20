package com.hcv.api_controller;

import com.hcv.dto.UserDTO;
import com.hcv.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserAPI {

    @Autowired
    private IUserService userService;

    @GetMapping("/showAllUsers")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> showListUsers() {
        List<UserDTO> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
