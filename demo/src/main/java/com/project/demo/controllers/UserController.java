package com.project.demo.controllers;

import com.project.demo.dtos.user.UserRegisterDto;
import com.project.demo.dtos.user.UserResponseDto;
import com.project.demo.services.UserService;
import groovy.util.logging.Slf4j;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@Slf4j
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public String getAll(Model model) {
        log.info("Request received to get all users.");
        List<UserResponseDto> usersList = userService.getAll();
        model.addAttribute("users", usersList);
        log.info("Number of users retrieved: " + usersList.size());
        return "users-list";
    }

    @GetMapping("/users/create")
    public String createUserForm(Model model) {
        log.info("Request received to create user form.");
        UserRegisterDto user = new UserRegisterDto();
        model.addAttribute("user", user);
        return "users-create";
    }

    @PostMapping("/users/create")
    public String saveUser(@Valid @ModelAttribute("user") UserRegisterDto user, Model model,
                           BindingResult result){

        if(result.hasErrors()) {
            model.addAttribute("error", "Error creating user");
            return "error-page";
        }
        log.info("Request received to save user: " + user.toString());
        userService.create(user);
        log.info("User saved successfully.");
        return "redirect:/users";
    }

}