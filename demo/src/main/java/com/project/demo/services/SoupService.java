package com.project.demo.services;

import com.project.demo.dtos.SoupCreateDto;
import com.project.demo.models.Soup;
import com.project.demo.models.SoupType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SoupService {


    public Integer create(SoupCreateDto request);
    public List<Soup> getAll();
    public Soup getSoupById(Integer id);
    public List<Soup> getSoupsByType(SoupType type);

    public List<Soup> filterSoupsByIngredients(List<String> ingredients);
    public Soup restockSoupById(Integer soupId, int stock);


    void updateSoup(Soup soup);
}
