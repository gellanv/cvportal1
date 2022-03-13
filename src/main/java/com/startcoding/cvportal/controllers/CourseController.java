package com.startcoding.cvportal.controllers;

import com.startcoding.cvportal.dto.CourseDto;
import com.startcoding.cvportal.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    @Autowired
    CourseService courseService;

    @GetMapping("/courses")
    public ResponseEntity<List<CourseDto>> getAll() {
        return courseService.getAllCourses();
    }

    @PostMapping("/courses")
    public ResponseEntity<CourseDto> add(@RequestBody CourseDto newCourseDto) {
        return courseService.addCours(newCourseDto);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<CourseDto> getById(@PathVariable Long id) {
        return courseService.findCoursById(id);
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<CourseDto> editById(@RequestBody CourseDto courseDto, @PathVariable Long id) {
        return courseService.editCours(courseDto, id);
    }
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        return courseService.deleteCoursById(id);
    }
}