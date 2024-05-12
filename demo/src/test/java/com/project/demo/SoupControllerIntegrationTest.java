package com.project.demo;

import com.project.demo.controllers.SoupController;
import com.project.demo.models.Soup;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class SoupControllerIntegrationTest {
    @Mock
    SoupRepository soupRepository;
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
        MockitoAnnotations.openMocks(this);
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
}
