package com.startcoding.cvportal.controllers;

import com.startcoding.cvportal.dto.WorkDto;
import com.startcoding.cvportal.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WorkController {
    @Autowired
    WorkService workService;

    @GetMapping("/cvs/{id}/works")
    public ResponseEntity<List<WorkDto>> getAll(@PathVariable Long id) {
        return workService.getAllWorks(id);
    }

    @PostMapping("/cvs/{id}/works")
    public ResponseEntity<WorkDto> add(@PathVariable Long id, @RequestBody WorkDto workDto) {
        return workService.addWork(workDto, id);
    }

    @GetMapping("/cvs/{id}/works/{id_work}")
    public ResponseEntity<WorkDto> getById(@PathVariable Long id, @PathVariable Long id_work) {
        return workService.findWorkById(id_work);
    }

    @PutMapping("/cvs/{id}/works/{id_work}")
    public ResponseEntity<WorkDto> editById(@RequestBody WorkDto workDto, @PathVariable Long id, @PathVariable Long id_work) {
        return workService.editWork(workDto, id_work);
    }

    @DeleteMapping("/works/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        return workService.deleteWorkById(id);
    }
}