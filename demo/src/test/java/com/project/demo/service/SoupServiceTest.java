package com.project.demo.service;

import com.project.demo.dtos.SoupCreateDto;
import com.project.demo.exceptions.DuplicateRowException;
import com.project.demo.models.Soup;
import com.project.demo.models.SoupType;
import com.project.demo.repositories.SoupRepository;
import com.project.demo.services.impl.SoupServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class SoupServiceTest {
    @Mock
    SoupRepository soupRepository;
    @InjectMocks
    SoupServiceImpl soupService;

    @Test
    public void getSoups(){
        List<Soup> soupsRet = new ArrayList<>();
        Soup soup2 = new Soup();
        soup2.setId(2);
        soup2.setName("ab");
        Soup soup1 = new Soup();
        soup1.setId(1);
        soup1.setName("aa");

        soupsRet.add(soup1);
        soupsRet.add(soup2);
        Page<Soup> soupsPageRet = new PageImpl<>(soupsRet, PageRequest.of(0, 5, Sort.by("name")), soupsRet.size());
        when(soupRepository.findAll(PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "name")))).thenReturn(soupsPageRet);

        var soupsPage = soupService.getAllSoups(5,0, "name");
        var soups =  soupsPage.getContent().stream().collect(Collectors.toList());
        assertEquals(soups.size(),2);
        verify(soupRepository, times(1)).findAll(PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "name")));

    }

    @Test
    public void testCreateUniqueSoup() throws DuplicateRowException {
        SoupCreateDto uniqueSoupDto = new SoupCreateDto();
        uniqueSoupDto.setName("Unique Soup");
        uniqueSoupDto.setPrice(1);
        uniqueSoupDto.setType(SoupType.BEEF);
        uniqueSoupDto.setStock(1);

         when(soupRepository.existsByName(uniqueSoupDto.getName())).thenReturn(false);

        when(soupRepository.save(any(Soup.class))).thenAnswer(invocation -> {
            Soup savedSoup = invocation.getArgument(0);
            savedSoup.setId(1);
            return savedSoup;
        });

        Integer createdId = soupService.create(uniqueSoupDto);
        verify(soupRepository, times(1)).save(any());
        assertNotNull(createdId);
    }
    @Test
    public void testCreateDuplicateSoup() {
        SoupCreateDto duplicateSoupDto = new SoupCreateDto();
        duplicateSoupDto.setName("Duplicate Soup");
        duplicateSoupDto.setPrice(2);
        duplicateSoupDto.setType(SoupType.CHICKEN);
        duplicateSoupDto.setStock(5);
        when(soupRepository.existsByName(duplicateSoupDto.getName())).thenReturn(true);

        assertThrows(DuplicateRowException.class, () -> soupService.create(duplicateSoupDto));

        verify(soupRepository, never()).save(any());
    }
    @Test
    public void testUpdateExistingSoup() {
        Soup existingSoup = new Soup();
        existingSoup.setId(1);
        existingSoup.setName("Existing Soup");
        existingSoup.setPrice(5.0);
        existingSoup.setType(SoupType.CHICKEN);
        existingSoup.setStock(10);

        when(soupRepository.findById(existingSoup.getId())).thenReturn(Optional.of(existingSoup));

        Soup updatedSoup = new Soup();
        updatedSoup.setId(existingSoup.getId());
        updatedSoup.setName("Updated Soup");
        updatedSoup.setPrice(7.0);
        updatedSoup.setType(SoupType.BEEF);
        updatedSoup.setStock(15);

        ArgumentCaptor<Soup> captor = ArgumentCaptor.forClass(Soup.class);
        when(soupRepository.save(captor.capture())).thenReturn(updatedSoup);
        soupService.updateSoup(updatedSoup);

        verify(soupRepository, times(1)).save(any(Soup.class));
        assertEquals(updatedSoup, captor.getValue());
    }


}
