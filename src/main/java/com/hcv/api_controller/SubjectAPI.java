package com.hcv.api_controller;

import com.hcv.dto.SubjectDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.SubjectInput;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.service.ISubjectService;
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
@RequestMapping("/subjects")
public class SubjectAPI {

    private final ISubjectService subjectService;

    @PostMapping("/insert")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ResponseEntity<?> insert(@RequestBody @Valid SubjectInput subjectInput) {
        SubjectDTO new_subjectDTO = subjectService.insert(subjectInput);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(new_subjectDTO).build(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id,
                                    @RequestBody @Valid SubjectInput subjectInput) {
        SubjectDTO old_subjectDTO = subjectService.findOneById(id);
        if (old_subjectDTO == null) {
            SubjectDTO new_subjectDTO = subjectService.insert(subjectInput);
            return new ResponseEntity<>(ApiResponse.builder().code(10000).result(new_subjectDTO).build(), HttpStatus.CREATED);
        }
        SubjectDTO updatedDTO = subjectService.update(old_subjectDTO, subjectInput);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(updatedDTO).build(), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('DEAN') or hasRole('CATECHISM')")
    public ResponseEntity<?> delete(@RequestBody Long[] ids) {
        subjectService.delete(ids);
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
        ShowAllResponse<?> response = subjectService.showAll(showAllRequest);
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(response).build(), HttpStatus.OK);
    }

    @GetMapping("/showAll-no-params")
    public ResponseEntity<?> showAll() {
        List<SubjectDTO> response = subjectService.findAll();
        if (response == null) {
            return new ResponseEntity<>(ApiResponse.builder().code(10000).message("No search item !").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(response).build(), HttpStatus.OK);
    }


    @GetMapping("/showOne")
    public ResponseEntity<?> showOne(@RequestParam(name = "name") String name) {
        SubjectDTO subjectDTO = subjectService.findOneByName(name);
        if (subjectDTO == null) {
            return new ResponseEntity<>(ApiResponse.builder().code(10000).message("No search item !").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ApiResponse.builder().code(10000).result(subjectDTO).build(), HttpStatus.OK);
    }

}
