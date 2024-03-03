package com.hcv.api_controller;

import com.hcv.api_controller.input.TeacherInput;
import com.hcv.dto.TeacherDTO;
import com.hcv.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/teachers")
public class TeacherAPI {

    @Autowired
    private ITeacherService teacherService;

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody TeacherInput teacherInput) {
        TeacherDTO teacherDTO = teacherService.findOneByMaSo(teacherInput.getMaSo());
        if (teacherDTO != null) {
            return new ResponseEntity<>("Teacher already exist !", HttpStatus.CONFLICT);
        }
        teacherService.insert(teacherInput);
        return new ResponseEntity<>("Successfully !", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody Long[] ids) {
        teacherService.delete(ids);
        return new ResponseEntity<>("Successfully !", HttpStatus.OK);
    }

    @GetMapping("/showAll")
    public ResponseEntity<?> showAll() {
        List<TeacherDTO> listResult = teacherService.showAll();
        return new ResponseEntity<>(listResult, HttpStatus.OK);
    }


}
