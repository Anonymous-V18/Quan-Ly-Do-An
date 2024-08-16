package com.hcv.api_controller;

import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.TeacherInput;
import com.hcv.dto.request.TeacherInsertFromFileInput;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.dto.response.TeacherDTO;
import com.hcv.dto.response.TeacherShowToSelectionResponse;
import com.hcv.service.ITeacherService;
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
@RequestMapping("/teachers")
public class TeacherController {

    IUserService userService;
    ITeacherService teacherService;


    @PostMapping("/insert-from-file")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<List<TeacherDTO>> insertFromFile(@RequestBody @Valid TeacherInsertFromFileInput teacherInsertFromFileInput) {
        List<TeacherDTO> response = teacherService.insertFromFile(teacherInsertFromFileInput);
        return ApiResponse.<List<TeacherDTO>>builder()
                .result(response)
                .build();
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM') or hasRole('TEACHER') or hasRole('HEAD_OF_DEPARTMENT')")
    public ApiResponse<TeacherDTO> update(@RequestBody @Valid TeacherInput teacherInput) {
        String teacherId = userService.getClaimsToken().get("sub").toString();
        TeacherDTO response = teacherService.update(teacherId, teacherInput);
        return ApiResponse.<TeacherDTO>builder()
                .result(response)
                .build();
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<String> delete(@RequestBody String[] ids) {
        teacherService.delete(ids);
        return ApiResponse.<String>builder()
                .message("Xóa giảng viên thành công !")
                .build();
    }

    @GetMapping("/showAll")
    public ApiResponse<ShowAllResponse<TeacherDTO>> showAll(@RequestParam(name = "page") Integer page,
                                                            @RequestParam(name = "limit") Integer limit,
                                                            @RequestParam(name = "orderBy") String orderBy,
                                                            @RequestParam(name = "orderDirection") String orderDirection) {
        ShowAllRequest showAllRequest = ShowAllRequest.builder()
                .page(page)
                .limit(limit)
                .orderBy(orderBy)
                .orderDirection(orderDirection)
                .build();
        ShowAllResponse<TeacherDTO> response = teacherService.showAll(showAllRequest);
        return ApiResponse.<ShowAllResponse<TeacherDTO>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showAll-to-selection")
    public ApiResponse<List<TeacherShowToSelectionResponse>> showAllToSelection() {
        List<TeacherShowToSelectionResponse> response = teacherService.showAllToSelection();
        return ApiResponse.<List<TeacherShowToSelectionResponse>>builder()
                .result(response)
                .build();
    }


    @GetMapping("/showOne")
    public ApiResponse<TeacherDTO> showOne(@RequestParam(name = "id") String id) {
        TeacherDTO response = teacherService.findOneById(id);
        return ApiResponse.<TeacherDTO>builder()
                .result(response)
                .build();
    }

    @GetMapping("/getMyInfo")
    public ApiResponse<TeacherDTO> getMyInfo() {
        String teacherId = userService.getClaimsToken().get("sub").toString();
        TeacherDTO response = teacherService.findOneById(teacherId);
        return ApiResponse.<TeacherDTO>builder()
                .result(response)
                .build();
    }


}
