package com.project.demo;

import com.project.demo.controllers.SoupController;
import com.project.demo.dtos.SoupCreateDto;
import com.project.demo.models.Soup;
import com.project.demo.models.SoupType;
import com.project.demo.repositories.SoupRepository;
import com.project.demo.services.impl.SoupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class SoupControllerIntegrationTest {
    @MockBean
    SoupServiceImpl soupService;
    MockMvc mockMvc;
    @Mock
    private Model model;
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new SoupController(soupService)).build();
    }

    @Test
    public void showByIdMvc() throws Exception{
        Integer id = 1;
        Soup soupTest = new Soup();
        soupTest.setId(id);
        soupTest.setName("soupTest name");
        when(soupService.getSoupById(id)).thenReturn(soupTest);

        mockMvc.perform(get("/soups/{id}","1"))
                .andExpect(status().isOk())
                .andExpect(view().name("soups-detail"))
                .andExpect(model().attribute("soup",soupTest));
    }
    @Test
    public void showByIdNotFound() throws Exception{
        mockMvc.perform(get("/soups/{id}", "800"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("notFoundException"));

    }
    @Test
    public void testSaveValidSoup() throws Exception {
        SoupCreateDto soupCreateDto = new SoupCreateDto();
        soupCreateDto.setName("Test Soup");
        soupCreateDto.setPrice(5.0);
        soupCreateDto.setType(SoupType.CHICKEN);
        soupCreateDto.setStock(10);

        Soup expectedSoup = new Soup();
        expectedSoup.setId(1);

        when(soupService.create(any(SoupCreateDto.class))).thenReturn(expectedSoup.getId());
        when(soupService.getSoupById(expectedSoup.getId())).thenReturn(expectedSoup);

        mockMvc.perform(post("/soups/create")
                        .flashAttr("soup", soupCreateDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/soupsList/0?pageSize=5&field=name"));

        verify(soupService, times(1)).create(soupCreateDto);

        Soup createdSoup = soupService.getSoupById(expectedSoup.getId());
        assertNotNull(createdSoup);
    }


}
