package com.startcoding.cvportal.controllers;

import com.startcoding.cvportal.dto.JuniorWorkDto;
import com.startcoding.cvportal.service.JuniorWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class JuniorController {
    @Autowired
    JuniorWorkService juniorWorkService;

    @GetMapping("/juniors")
    public ResponseEntity<List<JuniorWorkDto>> getAll() {
        return juniorWorkService.getAllJuniorWorks();
    }

    @PostMapping("/juniors")
    public ResponseEntity<JuniorWorkDto> add(@RequestBody JuniorWorkDto newJuniorWorkDto) {
        return juniorWorkService.addJuniorWork(newJuniorWorkDto);
    }
    @GetMapping("/juniors/{id}")
    public ResponseEntity<JuniorWorkDto> getById(@PathVariable Long id) {
        return juniorWorkService.findById(id);
    }

    @PutMapping("/juniors/{id}")
    public ResponseEntity<JuniorWorkDto> editById(@RequestBody JuniorWorkDto juniorWork, @PathVariable Long id) {
        return juniorWorkService.editJuniorWork(juniorWork, id);
    }

    @DeleteMapping("/juniors/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        return juniorWorkService.deleteJuniorWorkById(id);
    }
}