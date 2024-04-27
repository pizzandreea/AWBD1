package com.project.demo.controllers;

import com.project.demo.dtos.SoupCreateDto;
import com.project.demo.models.Soup;

import com.project.demo.models.User;
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

    @GetMapping("/soups/create")
    public String createForm(Model model) {
        SoupCreateDto soup = new SoupCreateDto();
        model.addAttribute("soup", soup);
        return "soups-create";
    }
    @PostMapping("/soups/create")
    public String save(@ModelAttribute("soup")SoupCreateDto soup) {
        soupService.create(soup);
        return "redirect:/soups";
    }
    @GetMapping("/soups")
    public String getAll(Model model) {
        List<Soup> soupsList = soupService.getAll();
        model.addAttribute("soups", soupsList);
        return "soups-list";
    }

    @GetMapping("/soups/{id}/edit")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        Soup soup = soupService.getSoupById(id);
        model.addAttribute("soup", soup);
        return "soups-edit";
    }
    @PostMapping("/soups/{id}/edit")
    public String update(@PathVariable("id")Integer id, @ModelAttribute("soup")Soup soup) {
        soup.setId(id);
        soupService.updateSoup(soup);
        return "redirect:/soups";
    }

    @GetMapping("/soups/{id}")
    public String SoupDetail(@PathVariable("id") Integer id, Model model){
        Soup soup = soupService.getSoupById(id);
        model.addAttribute("soup", soup);
        return "soups-detail";
    }
}
