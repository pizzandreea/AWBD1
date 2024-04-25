package com.project.demo.controllers;

import com.project.demo.dtos.SoupCreateDto;
import com.project.demo.models.Soup;
import com.project.demo.models.SoupType;
import com.project.demo.services.SoupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class SoupController {
    private final SoupService soupService;

    @Autowired
    public SoupController(SoupService soupService) {
        this.soupService = soupService;
    }

    @GetMapping("/getAllSoups")
    public String getAll(Model model) {
        List<Soup> soupsList = new ArrayList<Soup>();
        soupsList = soupService.getAll();
        model.addAttribute("soups", soupsList);
        return "soups-list";
    }
}
