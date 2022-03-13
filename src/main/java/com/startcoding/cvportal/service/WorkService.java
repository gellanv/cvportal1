package com.startcoding.cvportal.service;

import com.startcoding.cvportal.dto.WorkDto;
import com.startcoding.cvportal.models.CV;
import com.startcoding.cvportal.models.Work;
import com.startcoding.cvportal.repository.CVRepository;
import com.startcoding.cvportal.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class WorkService {

    @Autowired
    WorkRepository workRepository;
    @Autowired
    CVRepository cvRepository;

    public ResponseEntity<List<WorkDto>> getAllWorks(Long id) {
        Collection<Work> works = workRepository.findAllBycv_id(id);
        List<WorkDto> workDtos = new ArrayList<>();
        for (Work work : works) {
            WorkDto workDto = new WorkDto(work.getId(), work.getName(), work.getDescription(), work.getLink());
            workDtos.add(workDto);
        }
        return new ResponseEntity<List<WorkDto>>(workDtos, HttpStatus.OK);
    }

    public ResponseEntity<WorkDto> addWork(WorkDto workDto, Long id) {
        CV cv = cvRepository.getById(id);
        Work newWork = new Work(workDto.getName(), workDto.getDescription(), workDto.getLink(), cv);
        workDto.setId(newWork.getId());
        return new ResponseEntity<WorkDto>(workDto, HttpStatus.OK);
    }

    public ResponseEntity<WorkDto> findWorkById(Long id) {
        Optional<Work> work = workRepository.findById(id);
        if (work.isPresent()) {
            WorkDto workDto = new WorkDto(work.get().getId(), work.get().getName(), work.get().getDescription(), work.get().getLink());
            return new ResponseEntity<WorkDto>(workDto, HttpStatus.OK);
        } else return new ResponseEntity("Such work doesn't exist", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<WorkDto> editWork(WorkDto workDto, Long id) {
        Optional<Work> work = workRepository.findById(id);
        if (work.isPresent()) {
            work.get().setName(workDto.getName());
            work.get().setDescription(workDto.getDescription());
            work.get().setLink(workDto.getLink());
            workRepository.save(work.get());
            return new ResponseEntity<WorkDto>(workDto, HttpStatus.OK);
        } else return new ResponseEntity("Such work doesn't exist", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> deleteWorkById(Long id) {
        Optional<Work> work = workRepository.findById(id);
        if (work.isPresent()) {
            workRepository.delete(work.get());
            return new ResponseEntity("Work was removed", HttpStatus.OK);
        } else {
            return new ResponseEntity("Such work doesn't exist", HttpStatus.BAD_REQUEST);
        }
    }
}
