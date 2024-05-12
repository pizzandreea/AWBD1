package com.project.demo.service;

import com.project.demo.models.Soup;
import com.project.demo.repositories.SoupRepository;
import com.project.demo.services.impl.SoupServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
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

}
