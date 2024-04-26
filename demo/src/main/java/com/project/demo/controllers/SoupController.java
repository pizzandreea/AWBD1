package com.project.demo.controllers;

import com.project.demo.models.Soup;

import com.project.demo.services.SoupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin
public class SoupController {
    private final SoupService soupService;

    @Autowired
    public SoupController(SoupService soupService) {
        this.soupService = soupService;
    }

    @GetMapping("/getAllSoups")
    public String getAll(Model model) {
        List<Soup> soupsList = soupService.getAll();
        model.addAttribute("soups", soupsList);
        return "soups-list";
    }
}
