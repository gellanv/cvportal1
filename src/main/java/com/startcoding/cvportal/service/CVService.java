package com.startcoding.cvportal.service;

import com.startcoding.cvportal.dto.CVDto;
import com.startcoding.cvportal.models.CV;
import com.startcoding.cvportal.models.Technology;
import com.startcoding.cvportal.models.User;
import com.startcoding.cvportal.models.Work;
import com.startcoding.cvportal.repository.CVRepository;
import com.startcoding.cvportal.repository.TechnologyRepository;
import com.startcoding.cvportal.repository.UserRepository;
import com.startcoding.cvportal.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.util.List;

@Service
public class CVService {
    @Autowired
    CVRepository cvRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TechnologyRepository technologyRepository;
    @Autowired
    WorkRepository workRepository;

    public ResponseEntity<List<CVDto>> getAllCV() {
        Collection<CV> cvs = cvRepository.findAll();
        List<CVDto> cvDtos = new ArrayList<>();
        for (CV cv : cvs) {
            List<String> technologies = new ArrayList<>();
            for (Technology tch : cv.getTechnologies()) {
                technologies.add(tch.getName());
            }
            Map<Long, String> mapWorks = new HashMap<>();
            for (Work work : cv.getWorks()) {
                mapWorks.put(work.getId(), work.getName());
            }
            CVDto cvDto = new CVDto(cv.getId(), cv.getEducation(), cv.getCourses(), cv.getExperience(), cv.getAboutPerson(), mapWorks, technologies);
            cvDtos.add(cvDto);
        }
        return new ResponseEntity<List<CVDto>>(cvDtos, HttpStatus.OK);
    }

    public ResponseEntity<CVDto> addCV(CVDto cvDto, Long user_id) {
        Optional<User> user = userRepository.findById(user_id);
        if (user.get().getCv() == null) {
            CV newCv = new CV(cvDto.getEducation(), cvDto.getCourses(), cvDto.getExperience(), cvDto.getAboutPerson());
            cvRepository.save(newCv);
            user.get().setCv(newCv);
            for (String tech : cvDto.getTechnologies()) {
                Optional<Technology> technology = technologyRepository.findByName(tech);
                if (technology.isPresent())
                    technology.get().getCvs().add(newCv);
                technologyRepository.save(technology.get());
            }
            userRepository.save(user.get());
            return new ResponseEntity("You CV was added", HttpStatus.OK);
        } else return new ResponseEntity("You have already had CV", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<CVDto> getCvByUserId(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.get().getCv() == null) {
            return new ResponseEntity("You donn't have CV", HttpStatus.BAD_REQUEST);
        } else {
            List<String> technologies = new ArrayList<>();
            for (Technology tch : user.get().getCv().getTechnologies()) {
                technologies.add(tch.getName());
            }
            Map<Long, String> works = new HashMap<>();
            for (Work work : user.get().getCv().getWorks()) {
                works.put(work.getId(), work.getName());
            }
            CVDto cvDto = new CVDto(user.get().getCv().getId(), user.get().getCv().getEducation(), user.get().getCv().getCourses(), user.get().getCv().getExperience(), user.get().getCv().getAboutPerson(), works, technologies);
            return new ResponseEntity<CVDto>(cvDto, HttpStatus.OK);
        }
    }

    public ResponseEntity<CVDto> getCVDById(Long id) {
        Optional<CV> cv = cvRepository.findById(id);
        if (cv.isPresent()) {
            List<String> technologies = new ArrayList<>();
            for (Technology tch : cv.get().getTechnologies()) {
                technologies.add(tch.getName());
            }
            Map<Long, String> works = new HashMap<>();
            for (Work work : cv.get().getWorks()) {
                works.put(work.getId(), work.getName());
            }
            CVDto cvDto = new CVDto(cv.get().getId(), cv.get().getEducation(), cv.get().getCourses(), cv.get().getExperience(), cv.get().getAboutPerson(), works, technologies);
            return new ResponseEntity<CVDto>(cvDto, HttpStatus.OK);
        }
        return new ResponseEntity("Such CV doesn't exist", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<CVDto> editCV(CVDto cvDto, Long id) {
        Optional<CV> cv = cvRepository.findById(id);
        if (cv.isPresent()) {
            cv.get().setEducation(cvDto.getEducation());
            cv.get().setAboutPerson(cvDto.getAboutPerson());
            cv.get().setCourses(cvDto.getCourses());
            cv.get().setExperience(cvDto.getExperience());

            //Удаляем записи в технологиях по данному резюме, чтобы перезаписать новые
            Collection<Technology> technologies = technologyRepository.findAll();
            for (Technology technology : technologies) {
                technology.getCvs().remove(cv.get());
            }
            //Добавляем новые записи по технологиям по данному резюме
            List<String> newListTechnologies = new ArrayList<>();
            for (String tech : cvDto.getTechnologies()) {
                Optional<Technology> technology = technologyRepository.findByName(tech);
                if (technology.isPresent()) {
                    technology.get().getCvs().add(cv.get());
                    newListTechnologies.add(tech);
                }
            }
            cvDto.setTechnologies(newListTechnologies);
            cvRepository.save(cv.get());
            return new ResponseEntity<CVDto>(cvDto, HttpStatus.OK);
        } else return new ResponseEntity("Such cv doesn't exists", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> deleteCVById(Long id_cv, Long id_user) {
        Optional<CV> cv = cvRepository.findById(id_cv);
        if (cv.isPresent()) {
            //Удаляем записи в технологиях по данному резюме
            Collection<Technology> technologies = technologyRepository.findAll();
            for (Technology technology : technologies) {
                technology.getCvs().remove(cv.get());
            }
            //Удаляем все работы данного резюме
            Collection<Work> works = workRepository.findAllBycv_id(id_cv);
            workRepository.deleteAll(works);
            //Обнуляем поле id_cv user
            Optional<User> user = userRepository.findById(id_user);
            if (user.isPresent())
                user.get().setCv(null);
            //Удаляем Резюме
            cvRepository.delete(cv.get());
            return new ResponseEntity("CV was removed", HttpStatus.OK);
        }
        return new ResponseEntity("Such CV doesn't exist", HttpStatus.BAD_REQUEST);
    }
}
