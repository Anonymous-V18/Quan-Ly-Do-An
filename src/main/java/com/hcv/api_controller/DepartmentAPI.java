package com.hcv.api_controller;

import com.hcv.dto.DepartmentDTO;
import com.hcv.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/departments")
public class DepartmentAPI {

    @Autowired
    private IDepartmentService departmentService;

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO old_departmentDTO = departmentService.findOneByName(departmentDTO.getName());
        if (old_departmentDTO == null) {
            return new ResponseEntity<>(departmentService.insert(departmentDTO), HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Department already exist !", HttpStatus.CONFLICT);
    }

    @PutMapping("/update/{id}")
    public DepartmentDTO update(@PathVariable(value = "id") Long id,
                                @RequestBody DepartmentDTO new_departmentDTO) {
        DepartmentDTO old_departmentDTO = departmentService.findOneById(id);
        if (old_departmentDTO == null) {
            return departmentService.insert(new_departmentDTO);
        }
        return departmentService.update(new_departmentDTO, old_departmentDTO);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody Long[] ids) {
        departmentService.delete(ids);
    }

    @GetMapping("/showAll")
    public ResponseEntity<?> showAll() {
        List<DepartmentDTO> departmentDTOs = departmentService.showAll();
        return new ResponseEntity<>(departmentDTOs, HttpStatus.OK);
    }
}
