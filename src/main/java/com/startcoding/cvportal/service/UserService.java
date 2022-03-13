package com.startcoding.cvportal.service;

import com.startcoding.cvportal.dto.UserDto;
import com.startcoding.cvportal.models.User;
import com.startcoding.cvportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Object> register(UserDto userDto)
    {
        Optional user=userRepository.findByEmail(userDto.getEmail());
        if (user.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else
        {
            User newUser=new User(userDto.getEmail(), userDto.getPassword(), userDto.getName(), userDto.getSurname());
            userRepository.save(newUser);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

}
