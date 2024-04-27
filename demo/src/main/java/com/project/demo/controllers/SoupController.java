package com.project.demo.controllers;

import com.project.demo.dtos.SoupCreateDto;
import com.project.demo.dtos.SoupLightDto;
import com.project.demo.models.Soup;

import com.project.demo.services.SoupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        List<SoupLightDto> soupsList = soupService.getAll();
        model.addAttribute("soups", soupsList);
        return "soups-list";
    }

    @GetMapping("/soups/search")
    public String getSoupsByName(@RequestParam(value = "query") String query, Model model){
        List<SoupLightDto> lightSoups= soupService.findSoupsByName(query);
        model.addAttribute("soups", lightSoups);
        return "soups-list";
    }

    @GetMapping("/soups/sort")
    public String getSortedSoups(@RequestParam(value = "sortBy") String sortBy, Model model) {
        List<SoupLightDto> sortedSoups;
        switch (sortBy) {
            case "price_asc":
                sortedSoups = soupService.getSoupsSortedByPriceAsc();
                break;
            case "price_desc":
                sortedSoups = soupService.getSoupsSortedByPriceDesc();
                break;
            case "review_count":
                sortedSoups = soupService.getSoupsSortedByReviewCount();
                break;
            default:
                sortedSoups = soupService.getAll(); // Default to unsorted list
                break;
        }
        model.addAttribute("soups", sortedSoups);
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
    public String soupDetail(@PathVariable("id") Integer id, Model model){
        Soup soup = soupService.getSoupById(id);
        model.addAttribute("soup", soup);
        return "soups-detail";
    }
    @GetMapping("/soups/{id}/delete")
    public String deleteSoup(@PathVariable("id") Integer id){
        soupService.delete(id);
        return "redirect:/soups";
    }
}
