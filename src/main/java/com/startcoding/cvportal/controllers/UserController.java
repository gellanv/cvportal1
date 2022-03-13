package com.startcoding.cvportal.controllers;

import com.startcoding.cvportal.dto.UserDto;
import com.startcoding.cvportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("register")
    ResponseEntity<?> createUser(@RequestBody UserDto userDto)
    {
        return userService.register(userDto);
    }
}
