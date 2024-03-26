package com.hcv.api_controller;

import com.hcv.dto.StudentDTO;
import com.hcv.dto.UserDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.StudentFromExcelInput;
import com.hcv.dto.request.StudentInput;
import com.hcv.dto.request.UserRequest;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.service.IAuthService;
import com.hcv.service.IStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ResponseEntity<?> insertFromExcel(@RequestBody @Valid StudentFromExcelInput studentFromExcelInput) {
        for (StudentInput studentInput : studentFromExcelInput.getStudents()) {

            String usernameAndPasswordDefault = studentInput.getMaSo();
            UserRequest userRequest = new UserRequest();
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
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ResponseEntity<?> insert(@RequestBody @Valid StudentInput studentInput) {
        StudentDTO new_studentDTO = studentService.insert(studentInput);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(new_studentDTO).build(), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM') or hasRole('STUDENT')")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id,
                                    @RequestBody @Valid StudentInput studentInput) {
        StudentDTO old_studentDTO = studentService.findOneById(id);
        if (old_studentDTO == null) {
            StudentDTO new_studentDTO = studentService.insert(studentInput);
            return new ResponseEntity<>(ApiResponse.builder().code(10000).result(new_studentDTO).build(), HttpStatus.CREATED);
        }
        StudentDTO updatedDTO = studentService.update(old_studentDTO, studentInput);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(updatedDTO).build(), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ResponseEntity<?> delete(@RequestBody Long[] ids) {
        studentService.delete(ids);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).message("Deleted Successfully !").build(), HttpStatus.OK);
    }

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
        ShowAllResponse<?> response = studentService.showAll(showAllRequest);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(response).build(), HttpStatus.OK);
    }

    @GetMapping("/showAll-no-params")
    public ResponseEntity<?> showAll() {
        List<StudentDTO> response = studentService.findAll();
        if (response == null) {
            return new ResponseEntity<>(ApiResponse.builder().code(10000).message("No search item !").build(), HttpStatus.OK);
        }
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
