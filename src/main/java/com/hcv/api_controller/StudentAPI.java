package com.hcv.api_controller;

import com.hcv.dto.StudentDTO;
import com.hcv.dto.UserDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.StudentFromExcelInput;
import com.hcv.dto.request.StudentInput;
import com.hcv.dto.request.UserRequest;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.service.IAuthService;
import com.hcv.service.IStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentAPI {

    private final IStudentService studentService;
    private final IAuthService authService;

    @PostMapping("/insert-from-excel")
    public ResponseEntity<?> insert(@RequestBody @Valid StudentFromExcelInput studentFromExcelInput) {
        for (StudentInput studentInput : studentFromExcelInput.getStudents()) {

            UserRequest userRequest = new UserRequest();
            String usernameAndPasswordDefault = studentInput.getMaSo();
            userRequest.setUsername(usernameAndPasswordDefault);
            userRequest.setPassword(usernameAndPasswordDefault);
            userRequest.setNameRoles(List.of("HỌC SINH"));

            UserDTO userDTO = authService.createUser(userRequest);

            studentInput.setUser_id(userDTO.getId());

            studentService.insert(studentInput);
        }
        return new ResponseEntity<>(ApiResponse.builder().code(10000).message("Successfully !").build(), HttpStatus.CREATED);

    }

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody @Valid StudentInput studentInput) {
        StudentDTO studentDTO = studentService.findOneByMaSo(studentInput.getMaSo());
        if (studentDTO != null) {
            throw new AppException(ErrorCode.STUDENT_EXISTED);
        }
        StudentDTO new_studentDTO = studentService.insert(studentInput);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(new_studentDTO).build(), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody Long[] ids) {
        studentService.delete(ids);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).message("Deleted Successfully !").build(), HttpStatus.OK);
    }

    @GetMapping("/showAll")
    public ResponseEntity<?> showAll(@RequestBody @Valid ShowAllRequest showAllRequest) {
        ShowAllResponse<?> response = studentService.showAll(showAllRequest);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(response).build(), HttpStatus.OK);
    }

    @GetMapping("/showOne")
    public ResponseEntity<?> showOne(@RequestParam(name = "maSo") String maSo) {
        StudentDTO studentDTO = studentService.findOneByMaSo(maSo);
        if (studentDTO == null) {
            return new ResponseEntity<>(ApiResponse.builder().code(10000).message("No search item !").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(studentDTO).build(), HttpStatus.OK);
    }
}
