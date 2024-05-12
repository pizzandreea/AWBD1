package com.project.demo.services.impl;

import com.project.demo.dtos.SoupCreateDto;
import com.project.demo.dtos.SoupLightDto;
import com.project.demo.exceptions.DuplicateRowException;
import com.project.demo.exceptions.EntityNotFoundException;
import com.project.demo.exceptions.SoupRetrievalException;
import com.project.demo.models.Soup;
import com.project.demo.models.SoupType;
import com.project.demo.repositories.SoupRepository;
import com.project.demo.services.SoupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SoupServiceImpl implements SoupService {
    private final SoupRepository soupRepository;

    public SoupServiceImpl(SoupRepository soupRepository) {
        this.soupRepository = soupRepository;
    }


    @Override
    public Integer create(SoupCreateDto request) throws DuplicateRowException {
        if (soupRepository.existsByName(request.getName())) {
            throw new DuplicateRowException("Soup", "name", request.getName());
        }

        Soup soup = request.toSoup(new Soup());
        Soup createdSoup = soupRepository.save(soup);
        return createdSoup.getId();
    }


    @Override
    public List<SoupLightDto> getAll(){
        List<Soup> soups;
        try {
            soups = soupRepository.findAll();
        } catch (Exception e) {
            throw new SoupRetrievalException(e.getMessage());
        }
        return soups.stream().map(soup -> soup.toLightSoup()).collect(Collectors.toList());
    }
    public Page<SoupLightDto> getAllSoups(Integer pageSize,Integer offset, String field) {
        Page<Soup> soups = soupRepository.findAll(PageRequest.of(offset,pageSize).withSort(Sort.by(field)));
        List<SoupLightDto> lightSoups = soups.getContent().stream()
                .map(Soup::toLightSoup)
                .collect(Collectors.toList());

        return new PageImpl<>(lightSoups, soups.getPageable(), soups.getTotalElements());
    }

    @Override
    public Soup getSoupById(Integer id) {
        Optional<Soup> optionalSoup = soupRepository.findById(id);
        if(optionalSoup.isPresent()){
            return optionalSoup.get();
        }
        else{
            throw new EntityNotFoundException("Soup with ID " + id + " not found");
        }

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
    public List<SoupLightDto> findSoupsByName(String query) {
        List<Soup> soups = soupRepository.findByName(query);
        return soups.stream().map(soup -> soup.toLightSoup()).collect(Collectors.toList());
    }
    @Override
    public List<SoupLightDto> getSoupsSortedByPriceAsc() {
        List<Soup> soups = soupRepository.findAllByOrderByPriceAsc();
        return soups.stream().map(soup -> soup.toLightSoup()).collect(Collectors.toList());
    }

    @Override
    public List<SoupLightDto> getSoupsSortedByPriceDesc() {
        List<Soup> soups =  soupRepository.findAllByOrderByPriceDesc();
        return soups.stream().map(soup -> soup.toLightSoup()).collect(Collectors.toList());
    }

    @Override
    public List<SoupLightDto> getSoupsSortedByReviewCount() {
        List<Soup> soups =  soupRepository.findAllSortedByReviewCount();
        return soups.stream().map(soup -> soup.toLightSoup()).collect(Collectors.toList());
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

    @Override
    public void updateSoup(Soup soup) {
        Optional<Soup> existingSoupOptional = soupRepository.findById(soup.getId());
        existingSoupOptional.ifPresent(existingSoup -> {
            existingSoup.setName(soup.getName());
            existingSoup.setPrice(soup.getPrice());
            existingSoup.setStock(soup.getStock());
            existingSoup.setType(soup.getType());
            existingSoup.setIngredients(soup.getIngredients());
            soupRepository.save(existingSoup);
        });
    }

    @Override
    public void delete(Integer id) {
        soupRepository.deleteById((id));
    }


}
