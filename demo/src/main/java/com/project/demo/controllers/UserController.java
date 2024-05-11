package com.project.demo.controllers;

import com.project.demo.dtos.user.UserRegisterDto;
import com.project.demo.dtos.user.UserResponseDto;
import com.project.demo.models.User;
import com.project.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public String getAll(Model model) {
        System.out.println("Request received to get all users.");
        List<UserResponseDto> usersList = userService.getAll();
        model.addAttribute("users", usersList);
        System.out.println("Number of users retrieved: " + usersList.size());
        return "users-list";
    }

    @GetMapping("/users/create")
    public String createUserForm(Model model) {
        System.out.println("Request received to create user form.");
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
        System.out.println("Request received to save user: " + user.toString());
        userService.create(user);
        System.out.println("User saved successfully.");
        return "redirect:/users";
    }

}