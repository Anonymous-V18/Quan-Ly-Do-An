package com.hcv.api_controller;

import com.hcv.dto.TeacherDTO;
import com.hcv.dto.UserDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.TeacherFromExcelInput;
import com.hcv.dto.request.TeacherInput;
import com.hcv.dto.request.UserRequest;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.service.IAuthService;
import com.hcv.service.ITeacherService;
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
@RequestMapping("/teachers")
public class TeacherAPI {

    private final IAuthService authService;
    private final ITeacherService teacherService;


    @PostMapping("/insert-from-excel")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ResponseEntity<?> insertFromExcel(@RequestBody @Valid TeacherFromExcelInput teacherFromExcelInput) {
        for (TeacherInput teacherInput : teacherFromExcelInput.getTeachers()) {

            String usernameAndPasswordDefault = teacherInput.getMaSo();
            UserRequest userRequest = new UserRequest();
            userRequest.setUsername(usernameAndPasswordDefault);
            userRequest.setPassword(usernameAndPasswordDefault);
            userRequest.setNameRoles(List.of(teacherInput.getChucVu()));

            UserDTO userDTO = authService.createUser(userRequest);

            teacherInput.setUser_id(userDTO.getId());

            teacherService.insert(teacherInput);
        }
        return new ResponseEntity<>(ApiResponse.builder().code(10000).message("Successfully !").build(), HttpStatus.CREATED);

    }

    @PostMapping("/insert")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ResponseEntity<?> insert(@RequestBody @Valid TeacherInput teacherInput) {
        TeacherDTO new_TeacherDTO = teacherService.insert(teacherInput);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(new_TeacherDTO).build(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM') or hasRole('TEACHER') or hasRole('HEAD_OF_DEPARTMENT')")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id,
                                    @RequestBody @Valid TeacherInput teacherInput) {
        TeacherDTO old_teacherDTO = teacherService.findOneById(id);
        if (old_teacherDTO == null) {
            TeacherDTO new_teacherDTO = teacherService.insert(teacherInput);
            return new ResponseEntity<>(ApiResponse.builder().code(10000).result(new_teacherDTO).build(), HttpStatus.CREATED);
        }
        TeacherDTO updatedDTO = teacherService.update(old_teacherDTO, teacherInput);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(updatedDTO).build(), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ResponseEntity<?> delete(@RequestBody Long[] ids) {
        teacherService.delete(ids);
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
        ShowAllResponse<?> response = teacherService.showAll(showAllRequest);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(response).build(), HttpStatus.OK);
    }

    @GetMapping("/showAll-no-params")
    public ResponseEntity<?> showAll() {
        List<TeacherDTO> response = teacherService.findAll();
        if (response.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.builder().code(10000).message("No search item !").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(response).build(), HttpStatus.OK);
    }

    @GetMapping("/showOne")
    public ResponseEntity<?> showOne(@RequestParam(name = "maSo") String maSo) {
        TeacherDTO teacherDTO = teacherService.findOneByMaSo(maSo);
        if (teacherDTO == null) {
            return new ResponseEntity<>(ApiResponse.builder().code(10000).message("No search item !").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(teacherDTO).build(), HttpStatus.OK);
    }


}
