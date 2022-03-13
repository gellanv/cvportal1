package com.startcoding.cvportal.service;

import com.startcoding.cvportal.dto.TechnologyDto;
import com.startcoding.cvportal.models.*;
import com.startcoding.cvportal.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TechnologyService {
    @Autowired
    TechnologyRepository technologyRepository;

    public ResponseEntity<List<TechnologyDto>> getAllTechnology() {
        Collection<Technology> technologies = technologyRepository.findAll();

        List<TechnologyDto> technologyDtos = new ArrayList<>();
        for (Technology tech : technologies) {
            TechnologyDto technologyDto = new TechnologyDto(tech.getId(), tech.getName());
            technologyDtos.add(technologyDto);
        }
        return new ResponseEntity<List<TechnologyDto>>(technologyDtos, HttpStatus.OK);
    }

    public ResponseEntity<TechnologyDto> addTechnology(TechnologyDto technologyDto) {
        Optional technology = technologyRepository.findByName(technologyDto.getName());
        if (technology.isPresent()) {
            return new ResponseEntity("Such technology has already exist", HttpStatus.BAD_REQUEST);
        } else {
            Technology newTechnology = new Technology(technologyDto.getName());
            technologyRepository.save(newTechnology);
            technologyDto.setId(newTechnology.getId());
            return new ResponseEntity<TechnologyDto>(technologyDto, HttpStatus.OK);
        }
    }

    public ResponseEntity<TechnologyDto> findTechnologyById(Long id) {
        Optional<Technology> technology = technologyRepository.findById(id);
        if (technology.isPresent()) {
            Map<Long, String> juniorWorks = new HashMap<>();
            Map<Long, String> internships = new HashMap<>();
            Map<Long, String> courses = new HashMap<>();
            Map<Long, String> cvs = new HashMap<>();
            Map<Long, String> users = new HashMap<>();

            for (JuniorWork jw : technology.get().getJuniorWorks()) {
                juniorWorks.put(jw.getId(), jw.getName());
            }
            for (Internship intern : technology.get().getInternships()) {
                internships.put(intern.getId(), intern.getName());
            }
            for (Course crs : technology.get().getCourses()) {
                courses.put(crs.getId(), crs.getName());
            }
            for (CV cv : technology.get().getCvs()) {
                cvs.put(cv.getId(), cv.getUser().getName());
            }
            for (User user : technology.get().getUsers()) {
                users.put(user.getId(), user.getName());
            }
            TechnologyDto technologyDto = new TechnologyDto(technology.get().getId(),
                    technology.get().getName(), users, cvs, courses, internships, juniorWorks);

            return new ResponseEntity<TechnologyDto>(technologyDto, HttpStatus.OK);
        } else {
            return new ResponseEntity("Such technology doesn't exist", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<TechnologyDto> editTechnology(TechnologyDto technologyDto, Long id) {
        Optional<Technology> technology = technologyRepository.findById(id);
        if (technology.isPresent()) {
            technology.get().setName(technologyDto.getName());
            technologyRepository.save(technology.get());
            return new ResponseEntity<TechnologyDto>(technologyDto, HttpStatus.OK);
        } else {
            return new ResponseEntity("Such technology doesn't exist", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> deleteTechnologyById(Long id) {
        Optional<Technology> technology = technologyRepository.findById(id);
        if (technology.isPresent()) {
            technologyRepository.delete(technology.get());
            return new ResponseEntity("Technology was removed", HttpStatus.OK);
        } else {
            return new ResponseEntity("Such technology doesn't exist", HttpStatus.BAD_REQUEST);
        }
    }
}