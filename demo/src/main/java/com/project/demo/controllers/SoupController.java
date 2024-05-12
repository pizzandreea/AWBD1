package com.project.demo.controllers;

import com.project.demo.dtos.SoupCreateDto;
import com.project.demo.dtos.SoupLightDto;
import com.project.demo.exceptions.DuplicateRowException;
import com.project.demo.exceptions.EntityNotFoundException;
import com.project.demo.models.Soup;

import com.project.demo.services.SoupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public String save(@Valid @ModelAttribute("soup") SoupCreateDto soup,
                       BindingResult result) throws DuplicateRowException {
        if (result.hasErrors())
            return "soups-create";

        soupService.create(soup);
        return "redirect:/soupsList/0?pageSize=5&field=name";
    }

    @GetMapping("/soupsList/{offset}")
    public String getAllSoups(@RequestParam("pageSize") Integer pageSize,
                              @PathVariable("offset") Integer offset,
                              @RequestParam("field") String field,
                              Model model) {
        Page<SoupLightDto> soupsPage = soupService.getAllSoups(pageSize, offset, field);
        model.addAttribute("soups", soupsPage.getContent());
        model.addAttribute("totalPages", soupsPage.getTotalPages());
        model.addAttribute("offset", offset);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("field", field);
        return "soups-list";
    }

    @GetMapping("/soups/search")
    public String getSoupsByName(@RequestParam(value = "query") String query, Model model) {
        List<SoupLightDto> lightSoups = soupService.findSoupsByName(query);
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
    public String update(@PathVariable("id") Integer id, @ModelAttribute("soup") Soup soup) {
        soup.setId(id);
        soupService.updateSoup(soup);
        return "redirect:/soupsList/0?pageSize=5&field=name";
    }

    @GetMapping("/soups/{id}")
    public String soupDetail(@PathVariable("id") Integer id, Model model) {
        Soup soup = soupService.getSoupById(id);
        model.addAttribute("soup", soup);
        return "soups-detail";
    }

    @GetMapping("/soups/{id}/delete")
    public String deleteSoup(@PathVariable("id") Integer id) {
        soupService.delete(id);
        return "redirect:/soupsList/0?pageSize=5&field=name";
    }
}

