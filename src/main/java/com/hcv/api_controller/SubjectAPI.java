package com.hcv.api_controller;

import com.hcv.dto.SubjectDTO;
import com.hcv.dto.input.SubjectInput;
import com.hcv.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/subjects")
public class SubjectAPI {

    @Autowired
    private ISubjectService subjectService;

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody SubjectInput subjectInput) {
        SubjectDTO subjectDTO = subjectService.findOneByName(subjectInput.getName());
        if (subjectDTO == null) {
            subjectService.insert(subjectInput);
            return new ResponseEntity<>("Successfully !", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Subject already exist !", HttpStatus.CONFLICT);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id,
                                    @RequestBody SubjectInput subjectInput) {
        SubjectDTO old_subjectDTO = subjectService.findOneById(id);
        if (old_subjectDTO == null) {
            subjectService.insert(subjectInput);
            return new ResponseEntity<>("Successfully !", HttpStatus.CREATED);
        }
        subjectService.update(old_subjectDTO, subjectInput);
        return new ResponseEntity<>("Successfully !", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody Long[] ids) {
        subjectService.delete(ids);
        return new ResponseEntity<>("Successfully !", HttpStatus.OK);
    }

    @GetMapping("/showAll")
    public ResponseEntity<?> showAll() {
        List<SubjectDTO> subjectDTOs = subjectService.showAll();
        return new ResponseEntity<>(subjectDTOs, HttpStatus.OK);
    }

}
