package com.hcv.api_controller;

import com.hcv.dto.TeacherDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.TeacherInput;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.service.ITeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherAPI {

    private final ITeacherService teacherService;

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody @Valid TeacherInput teacherInput) {
        TeacherDTO teacherDTO = teacherService.findOneByMaSo(teacherInput.getMaSo());
        if (teacherDTO != null) {
            throw new AppException(ErrorCode.TEACHER_EXISTED);
        }
        TeacherDTO new_TeacherDTO = teacherService.insert(teacherInput);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(new_TeacherDTO).build(), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody Long[] ids) {
        teacherService.delete(ids);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).message("Deleted Successfully !").build(), HttpStatus.OK);
    }

    @GetMapping("/showAll")
    public ResponseEntity<?> showAll(@RequestBody @Valid ShowAllRequest showAllRequest) {
        ShowAllResponse<?> response = teacherService.showAll(showAllRequest);
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
