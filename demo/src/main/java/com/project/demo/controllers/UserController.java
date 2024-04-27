package com.project.demo.controllers;

import com.project.demo.dtos.user.UserRegisterDto;
import com.project.demo.dtos.user.UserResponseDto;
import com.project.demo.models.User;
import com.project.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        List<UserResponseDto> usersList = userService.getAll();
        model.addAttribute("users", usersList);
        return "users-list";
    }

    @GetMapping("/users/create")
    public String createUserForm(Model model) {
        UserRegisterDto user = new UserRegisterDto();
        model.addAttribute("user", user);
        return "users-create";
    }

    @PostMapping("/users/create")
    public String saveUser(@ModelAttribute("user") UserRegisterDto user){
        userService.create(user);
        return "redirect:/users";
    }

}