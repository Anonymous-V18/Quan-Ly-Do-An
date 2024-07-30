package com.hcv.api_controller;

import com.hcv.dto.TeacherDTO;
import com.hcv.dto.UserDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.teacher.TeacherFromExcelInput;
import com.hcv.dto.request.teacher.TeacherInput;
import com.hcv.dto.request.user.UserRequest;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.service.ITeacherService;
import com.hcv.service.IUserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/teachers")
public class TeacherAPI {

    IUserService userService;
    ITeacherService teacherService;


    @PostMapping("/insert-from-excel")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ApiResponse<String> insertFromExcel(@RequestBody @Valid TeacherFromExcelInput teacherFromExcelInput) {
        teacherService.checkDataBeforeInsert(teacherFromExcelInput);
        for (TeacherInput teacherInput : teacherFromExcelInput.getTeachers()) {

            String usernameAndPasswordDefault = teacherInput.getMaSo().trim();
            UserRequest userRequest = new UserRequest();
            userRequest.setUsername(usernameAndPasswordDefault);
            userRequest.setPassword(usernameAndPasswordDefault);
            userRequest.setNameRoles(Arrays.stream(teacherInput.getChucVu().split(",")).toList());

            UserDTO userDTO = userService.create(userRequest);

            teacherInput.setUserId(userDTO.getId());

            teacherService.insert(teacherInput);
        }
        return ApiResponse.<String>builder()
                .message("Thêm danh sách giảng viên thành công !")
                .build();

    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM') or hasRole('TEACHER') or hasRole('HEAD_OF_DEPARTMENT')")
    public ApiResponse<TeacherDTO> update(@PathVariable(value = "id") String id,
                                          @RequestBody @Valid TeacherInput teacherInput) {

        TeacherDTO updatedDTO = teacherService.update(id, teacherInput);
        return ApiResponse.<TeacherDTO>builder()
                .result(updatedDTO)
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

    @GetMapping("/showAll-no-params")
    public ApiResponse<List<TeacherDTO>> showAll() {
        List<TeacherDTO> response = teacherService.findAll();
        return ApiResponse.<List<TeacherDTO>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showOne")
    public ApiResponse<TeacherDTO> showOne(@RequestParam(name = "maSo") String maSo) {
        TeacherDTO teacherDTO = teacherService.findOneByMaSo(maSo);
        return ApiResponse.<TeacherDTO>builder()
                .result(teacherDTO)
                .build();
    }


}
