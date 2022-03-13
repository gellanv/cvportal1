package com.startcoding.cvportal.service;

import com.startcoding.cvportal.dto.JuniorWorkDto;
import com.startcoding.cvportal.models.JuniorWork;
import com.startcoding.cvportal.models.Technology;
import com.startcoding.cvportal.repository.JuniorWorkRepository;
import com.startcoding.cvportal.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class JuniorWorkService {

    @Autowired
    private JuniorWorkRepository juniorWorkRepository;
    @Autowired
    TechnologyRepository technologyRepository;

    public ResponseEntity<List<JuniorWorkDto>> getAllJuniorWorks() {
        Collection<JuniorWork> juniorWorks = juniorWorkRepository.findAll();
        List<JuniorWorkDto> juniorWorkDtos = new ArrayList<>();
        for (JuniorWork juniorWork : juniorWorks) {
            List<String> technologies = new ArrayList<>();
            for (Technology tch : juniorWork.getTechnologies()) {
                technologies.add(tch.getName());
            }
            JuniorWorkDto juniorWorkDto = new JuniorWorkDto(juniorWork.getId(), juniorWork.getName(), juniorWork.getDescription(), juniorWork.getLink(), technologies);
            juniorWorkDtos.add(juniorWorkDto);
        }
        return new ResponseEntity<List<JuniorWorkDto>>(juniorWorkDtos, HttpStatus.OK);
    }

    public ResponseEntity<JuniorWorkDto> addJuniorWork(JuniorWorkDto juniorWorkDto) {
        Optional newJuniorWork = juniorWorkRepository.findByLink(juniorWorkDto.getLink());
        if (newJuniorWork.isPresent()) {
            return new ResponseEntity("Such internship already exists", HttpStatus.BAD_REQUEST);
        } else {
            JuniorWork juniorWork = new JuniorWork(juniorWorkDto.getName(), juniorWorkDto.getDescription(), juniorWorkDto.getLink());
            for (String tech : juniorWorkDto.getTechnologies()) {
                Optional<Technology> technology = technologyRepository.findByName(tech);
                if (technology.isPresent())
                    technology.get().getJuniorWorks().add(juniorWork);
            }
            juniorWorkRepository.save(juniorWork);
            juniorWorkDto.setId(juniorWork.getId());
            return new ResponseEntity<JuniorWorkDto>(juniorWorkDto, HttpStatus.OK);
        }
    }

    public ResponseEntity<JuniorWorkDto> findById(Long id) {
        Optional<JuniorWork> juniorWork = juniorWorkRepository.findById(id);
        if (juniorWork.isPresent()) {
            List<String> technologies = new ArrayList<>();
            for (Technology tch : juniorWork.get().getTechnologies()) {
                technologies.add(tch.getName());
            }
            JuniorWorkDto juniorWorkDto = new JuniorWorkDto(juniorWork.get().getId(), juniorWork.get().getName(), juniorWork.get().getDescription(), juniorWork.get().getLink(), technologies);
            return new ResponseEntity<JuniorWorkDto>(juniorWorkDto, HttpStatus.OK);
        } else return new ResponseEntity("Such juniorWork doesn't exist", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<JuniorWorkDto> editJuniorWork(JuniorWorkDto juniorWorkDto, Long id) {
        Optional<JuniorWork> juniorWork = juniorWorkRepository.findById(id);
        if (juniorWork.isPresent()) {
            juniorWork.get().setName(juniorWorkDto.getName());
            juniorWork.get().setDescription(juniorWorkDto.getDescription());
            juniorWork.get().setLink(juniorWorkDto.getLink());

            //Удаляем записи в технологиях по данной интенрнатуре, чтобы перезаписать новые
            Collection<Technology> technologies = technologyRepository.findAll();
            for (Technology technology : technologies) {
                technology.getInternships().remove(juniorWork.get());
            }
            //Добавляем новые записи по технологиям по данному курсу
            List<String> newListTechnologies = new ArrayList<>();
            for (String tech : juniorWorkDto.getTechnologies()) {
                Optional<Technology> technology = technologyRepository.findByName(tech);
                if (technology.isPresent()) {
                    technology.get().getJuniorWorks().add(juniorWork.get());
                    newListTechnologies.add(tech);
                }
            }
            juniorWorkDto.setTechnologies(newListTechnologies);
            juniorWorkRepository.save(juniorWork.get());
            return new ResponseEntity<JuniorWorkDto>(juniorWorkDto, HttpStatus.OK);
        } else return new ResponseEntity("Such juniorWork doesn't exists", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> deleteJuniorWorkById(Long id) {
        Optional<JuniorWork> juniorWork = juniorWorkRepository.findById(id);
        if (juniorWork.isPresent()) {
            //Удаляем записи в технологиях по данной интенрнатуре, чтобы перезаписать новые
            Collection<Technology> technologies = technologyRepository.findAll();
            for (Technology technology : technologies) {
                technology.getJuniorWorks().remove(juniorWork.get());
            }
            juniorWorkRepository.delete(juniorWork.get());
            return new ResponseEntity("JuniorWork was removed", HttpStatus.OK);
        } else {
            return new ResponseEntity("Such JuniorWork doesn't exist", HttpStatus.BAD_REQUEST);
        }
    }
}
