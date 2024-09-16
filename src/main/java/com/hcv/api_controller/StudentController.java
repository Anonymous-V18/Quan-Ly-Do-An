package com.hcv.api_controller;

import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.StudentInput;
import com.hcv.dto.request.StudentInsertFromFileInput;
import com.hcv.dto.request.StudentNormalUpdateInput;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.StudentDTO;
import com.hcv.dto.response.StudentShowToSelectionResponse;
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
public class StudentController {

    IStudentService studentService;
    IUserService userService;

    @PostMapping("/insert-from-file")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<List<StudentDTO>> insertFromFile(@RequestBody @Valid StudentInsertFromFileInput studentInsertFromFileInput) {
        List<StudentDTO> response = studentService.insertFromFile(studentInsertFromFileInput);
        return ApiResponse.<List<StudentDTO>>builder()
                .result(response)
                .build();

    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('STUDENT')")
    public ApiResponse<StudentDTO> update(@RequestBody @Valid StudentNormalUpdateInput studentInput) {
        String studentId = userService.getClaimsToken().get("sub").toString();
        StudentDTO updatedDTO = studentService.update(studentId, studentInput);
        return ApiResponse.<StudentDTO>builder()
                .result(updatedDTO)
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<StudentDTO> updateAdvanced(@PathVariable(name = "id") String studentId,
                                                  @RequestBody @Valid StudentInput studentInput
    ) {
        StudentDTO updatedDTO = studentService.updateAdvanced(studentId, studentInput);
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
    public ApiResponse<ShowAllResponse<StudentDTO>> showAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(value = "orderBy", required = false, defaultValue = "id") String orderBy,
            @RequestParam(value = "orderDirection", required = false, defaultValue = "ASC") String orderDirection
    ) {
        ShowAllRequest showAllRequest = ShowAllRequest.builder()
                .currentPage(page)
                .limit(limit)
                .orderBy(orderBy)
                .orderDirection(orderDirection)
                .build();
        ShowAllResponse<StudentDTO> response = studentService.showAll(showAllRequest);
        return ApiResponse.<ShowAllResponse<StudentDTO>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showAll-to-selection")
    public ApiResponse<List<StudentShowToSelectionResponse>> showAllToSelection() {
        List<StudentShowToSelectionResponse> response = studentService.showAllToSelection();
        return ApiResponse.<List<StudentShowToSelectionResponse>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showOne")
    public ApiResponse<StudentDTO> showOne(@RequestParam(name = "id") String id) {
        StudentDTO response = studentService.findOneById(id);
        return ApiResponse.<StudentDTO>builder()
                .result(response)
                .build();
    }

    @GetMapping("/getMyInfo")
    @PreAuthorize("hasRole('STUDENT')")
    public ApiResponse<StudentDTO> getMyInfo() {
        String studentId = userService.getClaimsToken().get("sub").toString();
        StudentDTO studentDTO = studentService.findOneById(studentId);
        return ApiResponse.<StudentDTO>builder()
                .result(studentDTO)
                .build();
    }

}
