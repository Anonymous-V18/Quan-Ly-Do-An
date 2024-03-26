package com.hcv.api_controller;

import com.hcv.dto.DepartmentDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.service.IDepartmentService;
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
@RequestMapping("/departments")
public class DepartmentAPI {

    private final IDepartmentService departmentService;

    @PostMapping("/insert")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ResponseEntity<?> insert(@RequestBody @Valid DepartmentDTO departmentDTO) {
        DepartmentDTO old_departmentDTO = departmentService.findOneByName(departmentDTO.getName());
        if (old_departmentDTO != null) {
            throw new AppException(ErrorCode.DEPARTMENT_EXISTED);
        }
        DepartmentDTO new_DepartmentDTO = departmentService.insert(departmentDTO);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(new_DepartmentDTO).build(), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id,
                                    @RequestBody @Valid DepartmentDTO new_departmentDTO) {
        DepartmentDTO old_departmentDTO = departmentService.findOneById(id);
        if (old_departmentDTO == null) {
            DepartmentDTO new_DepartmentDTO = departmentService.insert(new_departmentDTO);
            return new ResponseEntity<>(ApiResponse.builder().code(10000).result(new_DepartmentDTO).build(), HttpStatus.CREATED);
        }
        DepartmentDTO updatedDTO = departmentService.update(new_departmentDTO, old_departmentDTO);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(updatedDTO).build(), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ResponseEntity<?> delete(@RequestBody Long[] ids) {
        departmentService.delete(ids);
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
        ShowAllResponse<DepartmentDTO> resultList = departmentService.showAll(showAllRequest);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(resultList).build(), HttpStatus.OK);
    }

    @GetMapping("/showAll-no-params")
    public ResponseEntity<?> showAll() {
        List<DepartmentDTO> response = departmentService.findAll();
        if (response == null) {
            return new ResponseEntity<>(ApiResponse.builder().code(10000).message("No search item !").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(response).build(), HttpStatus.OK);
    }


    @GetMapping("/showOne")
    public ResponseEntity<?> showOne(@RequestParam(name = "name") String name) {
        DepartmentDTO dto = departmentService.findOneByName(name);
        if (dto == null) {
            return new ResponseEntity<>(ApiResponse.builder().code(10000).message("No search item !").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(dto).build(), HttpStatus.OK);
    }


}
