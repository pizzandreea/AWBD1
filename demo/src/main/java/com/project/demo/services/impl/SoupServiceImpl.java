package com.project.demo.services.impl;

import com.project.demo.dtos.SoupCreateDto;
import com.project.demo.models.Soup;
import com.project.demo.models.SoupType;
import com.project.demo.repositories.SoupRepository;
import com.project.demo.services.SoupService;

import java.util.List;
import java.util.Optional;

public class SoupServiceImpl implements SoupService {
    private final SoupRepository soupRepository;

    public SoupServiceImpl(SoupRepository soupRepository) {
        this.soupRepository = soupRepository;
    }


    @Override
    public Integer create(SoupCreateDto request) {
        var soups = soupRepository.findAll();
        if(soups.stream().noneMatch(x -> x.getName().equals(request.getName()))){
            Soup soup = request.toSoup(new Soup());
            var createdsoup = soupRepository.save(soup);
            return createdsoup.getId();
        }
        return null;
    }

    @Override
    public List<Soup> getAll() {
        var soups = soupRepository.findAll();
        return soups;
    }

    @Override
    public List<Soup> getSoupsByType(SoupType type) {
        return soupRepository.findByType(type);
    }

    @Override
    public List<Soup> filterSoupsByIngredients(List<String> ingredients) {
        return soupRepository.findByIngredients(ingredients);
    }

    @Override
    public Soup restockSoupById(Integer soupId, int stock) {
        Optional<Soup> optionalSoup = soupRepository.findById(soupId);

        if (optionalSoup.isPresent()) {
            Soup soup = optionalSoup.get();
            int updatedStock = soup.getStock() + stock;
            soup.setStock(updatedStock);
            Soup updatedSoup = soupRepository.save(soup);
            return updatedSoup;
        } else {
            throw new IllegalArgumentException("Soup with ID " + soupId + " not found");
        }
    }
}
