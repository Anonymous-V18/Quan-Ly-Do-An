package com.hcv.api_controller;

import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.StudentFromExcelInput;
import com.hcv.dto.request.StudentInput;
import com.hcv.dto.request.UserRequest;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.StudentDTO;
import com.hcv.dto.response.UserDTO;
import com.hcv.service.IStudentService;
import com.hcv.service.IUserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/students")
public class StudentAPI {

    IStudentService studentService;
    IUserService userService;

    @PostMapping("/insert-from-excel")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<String> insertFromExcel(@RequestBody @Valid StudentFromExcelInput studentFromExcelInput) {
        studentService.checkDataBeforeInsert(studentFromExcelInput);
        for (StudentInput studentInput : studentFromExcelInput.getStudents()) {

            String usernameAndPasswordDefault = studentInput.getMaSo().trim();
            UserRequest userRequest = new UserRequest();
            userRequest.setUsername(usernameAndPasswordDefault);
            userRequest.setPassword(usernameAndPasswordDefault);
            userRequest.setNameRoles(List.of("SINH VIÊN"));

            UserDTO userDTO = userService.create(userRequest);

            studentInput.setUser_id(userDTO.getId());

            studentService.insert(studentInput);
        }
        return ApiResponse.<String>builder()
                .message("Thêm danh sách sinh viên thành công !")
                .build();

    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM') or hasRole('STUDENT')")
    public ApiResponse<StudentDTO> update(@PathVariable(value = "id") String id,
                                          @RequestBody @Valid StudentInput studentInput) {
        StudentDTO updatedDTO = studentService.update(id, studentInput);
        return ApiResponse.<StudentDTO>builder()
                .result(updatedDTO)
                .build();
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<String> delete(@RequestBody String[] ids) {
        studentService.delete(ids);
        return ApiResponse.<String>builder()
                .message("Xóa sinh viên thành công !")
                .build();
    }

    @GetMapping("/showAll")
    public ApiResponse<ShowAllResponse<StudentDTO>> showAll(@RequestParam(name = "page") Integer page,
                                                            @RequestParam(name = "limit") Integer limit,
                                                            @RequestParam(name = "orderBy") String orderBy,
                                                            @RequestParam(name = "orderDirection") String orderDirection) {
        ShowAllRequest showAllRequest = ShowAllRequest.builder()
                .page(page)
                .limit(limit)
                .orderBy(orderBy)
                .orderDirection(orderDirection)
                .build();
        ShowAllResponse<StudentDTO> response = studentService.showAll(showAllRequest);
        return ApiResponse.<ShowAllResponse<StudentDTO>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showAll-no-params")
    public ApiResponse<List<StudentDTO>> showAll() {
        List<StudentDTO> response = studentService.findAll();
        return ApiResponse.<List<StudentDTO>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showOne")
    public ApiResponse<StudentDTO> showOne(@RequestParam(name = "maSo") String maSo) {
        StudentDTO studentDTO = studentService.findOneByMaSo(maSo);
        return ApiResponse.<StudentDTO>builder()
                .result(studentDTO)
                .build();
    }
}
