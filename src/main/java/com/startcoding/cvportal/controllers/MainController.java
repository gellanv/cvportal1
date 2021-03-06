package com.startcoding.cvportal.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute("title", "Start Coding");
        return "home";
    }
}