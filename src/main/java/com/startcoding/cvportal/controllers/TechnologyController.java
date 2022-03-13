package com.startcoding.cvportal.controllers;

import com.startcoding.cvportal.dto.TechnologyDto;
import com.startcoding.cvportal.service.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TechnologyController {
    @Autowired
    TechnologyService technologyService;

    @GetMapping("/technologies")
    public ResponseEntity<List<TechnologyDto>> getAll() {
        return technologyService.getAllTechnology();
    }

    @PostMapping("/technologies")
    public ResponseEntity<TechnologyDto> add(@RequestBody TechnologyDto newTechnologyDto) {
        return technologyService.addTechnology(newTechnologyDto);
    }
    @GetMapping("/technologies/{id}")
    public ResponseEntity<TechnologyDto> getById(@PathVariable Long id) {
        return technologyService.findTechnologyById(id);
    }

    @PutMapping("/technologies/{id}")
    public ResponseEntity<TechnologyDto> editById(@RequestBody TechnologyDto technologyDto, @PathVariable Long id) {
        return technologyService.editTechnology(technologyDto, id);
    }
    @DeleteMapping("/technologies/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        return technologyService.deleteTechnologyById(id);
    }
}