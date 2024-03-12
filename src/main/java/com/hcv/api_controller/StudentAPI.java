package com.hcv.api_controller;

import com.hcv.dto.StudentDTO;
import com.hcv.dto.UserDTO;
import com.hcv.dto.request.StudentFromExcelInput;
import com.hcv.dto.request.StudentInput;
import com.hcv.dto.request.UserRequest;
import com.hcv.service.IAuthService;
import com.hcv.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/students")
public class StudentAPI {

    @Autowired
    private IStudentService studentService;
    @Autowired
    private IAuthService authService;

    @PostMapping("/insert-from-excel")
    public ResponseEntity<?> insert(@RequestBody StudentFromExcelInput studentFromExcelInput) {
        for (StudentInput studentInput : studentFromExcelInput.getStudents()) {

            UserRequest signInInput = new UserRequest();
            String usernameAndPasswordDefault = studentInput.getMaSo();
            signInInput.setUsername(usernameAndPasswordDefault);
            signInInput.setPassword(usernameAndPasswordDefault);
            signInInput.setNameRoles(List.of("HỌC SINH"));

            UserDTO userDTO = authService.createUser(signInInput);

            studentInput.setUser_id(userDTO.getId());

            studentService.insert(studentInput);
        }
        return new ResponseEntity<>("Successfully !", HttpStatus.CREATED);
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody StudentInput studentInput) {
        StudentDTO studentDTO = studentService.findOneByMaSo(studentInput.getMaSo());
        if (studentDTO == null) {
            studentService.insert(studentInput);
            return new ResponseEntity<>("Successfully !", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Student already exist !", HttpStatus.CONFLICT);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody Long[] ids) {
        studentService.delete(ids);
        return new ResponseEntity<>("Successfully !", HttpStatus.OK);
    }


    @GetMapping("/showAll")
    public List<StudentDTO> showAll() {
        return studentService.showAll();
    }
}
