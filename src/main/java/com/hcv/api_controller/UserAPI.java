package com.hcv.api_controller;

import com.hcv.dto.UserDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserAPI {

    private final IUserService userService;

    @GetMapping("/showAll")
    public ResponseEntity<?> showAll(@RequestParam(name = "page") Integer page,
                                     @RequestParam(name = "limit") Integer limit,
                                     @RequestParam(name = "orderBy") String orderBy,
                                     @RequestParam(name = "orderDirection") String orderDirection) {
        ShowAllRequest showAllRequest = ShowAllRequest.builder()
                .page(page)
                .limit(limit)
                .orderBy(orderBy)
                .orderDirection(orderDirection)
                .build();
        ShowAllResponse<?> response = userService.showAllUserResponse(showAllRequest);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(response).build(), HttpStatus.OK);
    }

    @GetMapping("/showAll-no-params")
    public ResponseEntity<?> showAll() {
        List<UserDTO> response = userService.findAll();
        if (response == null) {
            return new ResponseEntity<>(ApiResponse.builder().code(10000).message("No search item !").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(response).build(), HttpStatus.OK);
    }

    @GetMapping("/showOne")
    public ResponseEntity<?> showOne(@RequestParam(name = "userName") String userName) {
        UserDTO response = userService.findOneByUsername(userName);
        if (response == null) {
            return new ResponseEntity<>(ApiResponse.builder().code(10000).message("No search item !").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(response).build(), HttpStatus.OK);
    }

}
