package com.project.demo.services;

import com.project.demo.dtos.SoupCreateDto;
import com.project.demo.dtos.SoupLightDto;
import com.project.demo.models.Soup;
import com.project.demo.models.SoupType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SoupService {


    public Integer create(SoupCreateDto request);
    public List<SoupLightDto> getAll();
    public Page<SoupLightDto> getAllSoups(Integer pageSize, Integer offset, String field);
    public Soup getSoupById(Integer id);
    public List<Soup> getSoupsByType(SoupType type);
    public List<SoupLightDto> getSoupsSortedByPriceAsc();
    public List<SoupLightDto> getSoupsSortedByPriceDesc();
    public List<SoupLightDto> getSoupsSortedByReviewCount();

    public List<Soup> filterSoupsByIngredients(List<String> ingredients);
    public List<SoupLightDto> findSoupsByName(String query);
    public Soup restockSoupById(Integer soupId, int stock);
    void updateSoup(Soup soup);
    void delete(Integer id);
}
