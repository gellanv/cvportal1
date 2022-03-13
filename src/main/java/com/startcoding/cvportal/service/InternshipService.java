package com.startcoding.cvportal.service;

import com.startcoding.cvportal.dto.InternshipDto;
import com.startcoding.cvportal.models.Internship;
import com.startcoding.cvportal.models.Technology;
import com.startcoding.cvportal.repository.InternshipRepository;
import com.startcoding.cvportal.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InternshipService {
    @Autowired
    InternshipRepository internshipRepository;
    @Autowired
    TechnologyRepository technologyRepository;

    public ResponseEntity<List<InternshipDto>> getAllInternship() {
        Collection<Internship> internships = internshipRepository.findAll();
        List<InternshipDto> internshipDtos = new ArrayList<>();
        for (Internship internship : internships) {
            List<String> technologies = new ArrayList<>();
            for (Technology tch : internship.getTechnologies()) {
                technologies.add(tch.getName());
            }
            InternshipDto internshipDto = new InternshipDto(internship.getId(), internship.getName(), internship.getDescription(), internship.getLink(), technologies);
            internshipDtos.add(internshipDto);
        }
        return new ResponseEntity<List<InternshipDto>>(internshipDtos, HttpStatus.OK);
    }

    public ResponseEntity<InternshipDto> addInternship(InternshipDto newInternshipDto) {
        Optional internship = internshipRepository.findByLink(newInternshipDto.getLink());
        if (internship.isPresent()) {
            return new ResponseEntity("Such internship already exists", HttpStatus.BAD_REQUEST);
        } else {
            Internship newInternship = new Internship(newInternshipDto.getName(), newInternshipDto.getDescription(), newInternshipDto.getLink());
            for (String tech : newInternshipDto.getTechnologies()) {
                Optional<Technology> technology = technologyRepository.findByName(tech);
                if (technology.isPresent())
                    technology.get().getInternships().add(newInternship);
            }
            internshipRepository.save(newInternship);
            newInternshipDto.setId(newInternship.getId());
            return new ResponseEntity<InternshipDto>(newInternshipDto, HttpStatus.OK);
        }
    }

    public ResponseEntity<InternshipDto> findInternshipById(Long id) {
        Optional<Internship> internship = internshipRepository.findById(id);
        if (internship.isPresent()) {
            List<String> technologies = new ArrayList<>();
            for (Technology tch : internship.get().getTechnologies()) {
                technologies.add(tch.getName());
            }
            InternshipDto internshipDto = new InternshipDto(internship.get().getId(), internship.get().getName(), internship.get().getDescription(), internship.get().getLink(), technologies);
            return new ResponseEntity<InternshipDto>(internshipDto, HttpStatus.OK);
        } else return new ResponseEntity("Such internship doesn't exist", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<InternshipDto> editInternship(InternshipDto internshipDto, Long id) {
        Optional<Internship> internship = internshipRepository.findById(id);
        if (internship.isPresent()) {
            internship.get().setName(internshipDto.getName());
            internship.get().setDescription(internshipDto.getDescription());
            internship.get().setLink(internshipDto.getLink());

            //Удаляем записи в технологиях по данной интенрнатуре, чтобы перезаписать новые
            Collection<Technology> technologies = technologyRepository.findAll();
            for (Technology technology : technologies) {
                technology.getInternships().remove(internship.get());
            }
            //Добавляем новые записи по технологиям по данному курсу
            List<String> newListTechnologies = new ArrayList<>();
            for (String tech : internshipDto.getTechnologies()) {
                Optional<Technology> technology = technologyRepository.findByName(tech);
                if (technology.isPresent()) {
                    technology.get().getInternships().add(internship.get());
                    newListTechnologies.add(tech);
                }
            }
            internshipDto.setTechnologies(newListTechnologies);
            internshipRepository.save(internship.get());
            return new ResponseEntity<InternshipDto>(internshipDto, HttpStatus.OK);
        } else return new ResponseEntity("Such internship doesn't exists", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> deleteInternshipById(Long id) {
        Optional<Internship> internship = internshipRepository.findById(id);
        if (internship.isPresent()) {
            //Удаляем записи в технологиях по данной интернатуре
            Collection<Technology> technologies = technologyRepository.findAll();
            for (Technology technology : technologies) {
                technology.getInternships().remove(internship.get());
            }
            internshipRepository.delete(internship.get());
            return new ResponseEntity("Internship was removed", HttpStatus.OK);
        } else {
            return new ResponseEntity("Such cours doesn't exist", HttpStatus.BAD_REQUEST);
        }
    }
}