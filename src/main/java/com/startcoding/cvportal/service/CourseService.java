package com.startcoding.cvportal.service;

import com.startcoding.cvportal.dto.CourseDto;
import com.startcoding.cvportal.models.Course;
import com.startcoding.cvportal.models.Technology;
import com.startcoding.cvportal.repository.CourseRepository;
import com.startcoding.cvportal.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    TechnologyRepository technologyRepository;

    public ResponseEntity<List<CourseDto>> getAllCourses() {
        Collection<Course> courses = courseRepository.findAll();
        List<CourseDto> courseDtos = new ArrayList<>();
        for (Course course : courses) {
            List<String> technologies = new ArrayList<>();
            for (Technology tch : course.getTechnologies()) {
                technologies.add(tch.getName());
            }
            CourseDto courseDto = new CourseDto(course.getId(), course.getName(), course.getDescription(), course.getLink(), technologies);
            courseDtos.add(courseDto);
        }
        return new ResponseEntity<List<CourseDto>>(courseDtos, HttpStatus.OK);
    }

    public ResponseEntity<CourseDto> addCours(CourseDto newCourseDto) {
        Optional cours = courseRepository.findByLink(newCourseDto.getLink());
        if (cours.isPresent()) {
            return new ResponseEntity("Such cours already exists", HttpStatus.BAD_REQUEST);
        } else {
            Course newCourse = new Course(newCourseDto.getName(), newCourseDto.getDescription(), newCourseDto.getLink());
            for (String tech : newCourseDto.getTechnologies()) {
                Optional<Technology> technology = technologyRepository.findByName(tech);
                if (technology.isPresent())
                    technology.get().getCourses().add(newCourse);
            }
            courseRepository.save(newCourse);
            newCourseDto.setId(newCourse.getId());
            return new ResponseEntity<CourseDto>(newCourseDto, HttpStatus.OK);
        }
    }

    public ResponseEntity<CourseDto> findCoursById(Long id) {
        Optional<Course> cours = courseRepository.findById(id);
        if (cours.isPresent()) {
            List<String> technologies = new ArrayList<>();
            for (Technology tch : cours.get().getTechnologies()) {
                technologies.add(tch.getName());
            }
            CourseDto courseDto = new CourseDto(cours.get().getId(), cours.get().getName(), cours.get().getDescription(), cours.get().getLink(), technologies);
            return new ResponseEntity<CourseDto>(courseDto, HttpStatus.OK);
        } else return new ResponseEntity("Such cours doesn't exist", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<CourseDto> editCours(CourseDto courseDto, Long id) {
        Optional<Course> cours = courseRepository.findById(id);
        if (cours.isPresent()) {
            cours.get().setName(courseDto.getName());
            cours.get().setDescription(courseDto.getDescription());
            cours.get().setLink(courseDto.getLink());

            //Удаляем записи в технологиях по данному курсу, чтобы перезаписать новые
            Collection<Technology> technologies = technologyRepository.findAll();
            for (Technology technology : technologies) {
                technology.getCourses().remove(cours.get());
            }
            //Добавляем новые записи по технологиям по данному курсу
            List<String> newListTechnologies = new ArrayList<>();
            for (String tech : courseDto.getTechnologies()) {
                Optional<Technology> technology = technologyRepository.findByName(tech);
                if (technology.isPresent()) {
                    technology.get().getCourses().add(cours.get());
                    newListTechnologies.add(tech);
                }
            }
            courseDto.setTechnologies(newListTechnologies);
            courseRepository.save(cours.get());
            return new ResponseEntity<CourseDto>(courseDto, HttpStatus.OK);
        } else return new ResponseEntity("Such cours doesn't exists", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> deleteCoursById(Long id) {
        Optional<Course> cours = courseRepository.findById(id);
        if (cours.isPresent()) {
            //Удаляем записи в технологиях по данному курсу, чтобы перезаписать новые
            Collection<Technology> technologies = technologyRepository.findAll();
            for (Technology technology : technologies) {
                technology.getCourses().remove(cours.get());
            }
            courseRepository.delete(cours.get());
            return new ResponseEntity("Cours was removed", HttpStatus.OK);
        } else return new ResponseEntity("Such cours doesn't exist", HttpStatus.BAD_REQUEST);
    }
}