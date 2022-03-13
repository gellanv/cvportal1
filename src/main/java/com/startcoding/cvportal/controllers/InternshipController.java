package com.startcoding.cvportal.controllers;

import com.startcoding.cvportal.dto.InternshipDto;
import com.startcoding.cvportal.service.InternshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InternshipController {
    @Autowired
    InternshipService internshipService;

    @GetMapping("/internships")
    public ResponseEntity<List<InternshipDto>> getAll() {
        return internshipService.getAllInternship();
    }

    @PostMapping("/internships")
    public ResponseEntity<InternshipDto> add(@RequestBody InternshipDto newInternshipDto) {
        return internshipService.addInternship(newInternshipDto);
    }

    @GetMapping("/internships/{id}")
    public ResponseEntity<InternshipDto> getById(@PathVariable Long id) {return internshipService.findInternshipById(id);}

    @PutMapping("/internships/{id}")
    public ResponseEntity<InternshipDto> editById(@RequestBody InternshipDto internshipDto, @PathVariable Long id) {
        return internshipService.editInternship(internshipDto, id);
    }

    @DeleteMapping("/internships/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        return internshipService.deleteInternshipById(id);
    }
}