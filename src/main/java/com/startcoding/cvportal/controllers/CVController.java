package com.startcoding.cvportal.controllers;

import com.startcoding.cvportal.dto.CVDto;
import com.startcoding.cvportal.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CVController {
    @Autowired
    CVService cvService;

    @GetMapping("/cvs")
    public ResponseEntity<List<CVDto>> getAll() {
        return cvService.getAllCV();
    }

    @PostMapping("users/{id}/cvs")
    public ResponseEntity<CVDto> add(@RequestBody CVDto newCVDto, @PathVariable Long id) {
        return cvService.addCV(newCVDto, id);
    }

    @GetMapping("users/{id}/cvs")
    public ResponseEntity<CVDto> getCvByUser(@PathVariable Long id) {
        return cvService.getCvByUserId(id);
    }

    @GetMapping("/cvs/{id}")
    public ResponseEntity<CVDto> getCVDById(@PathVariable Long id) {
        return cvService.getCVDById(id);
    }

    @PutMapping("users/{id_user}/cvs/{id_cv}")
    public ResponseEntity<CVDto> editById(@RequestBody CVDto cvDto, @PathVariable Long id_cv) {
        return cvService.editCV(cvDto, id_cv);
    }

    @DeleteMapping("users/{id_user}/cvs/{id_cv}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id_cv, @PathVariable Long id_user) {
        return cvService.deleteCVById(id_cv, id_user);
    }
}