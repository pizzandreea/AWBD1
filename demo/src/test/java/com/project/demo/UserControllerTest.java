package com.project.demo;

import com.project.demo.controllers.UserController;
import com.project.demo.dtos.user.UserRegisterDto;
import com.project.demo.dtos.user.UserResponseDto;
import com.project.demo.models.User;
import com.project.demo.repositories.UserRepository;
import com.project.demo.services.UserService;
import com.project.demo.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        userService = new UserServiceImpl(userRepository);
//    }

    @Test
    void getAll_ReturnsCorrectView() {
        MockitoAnnotations.openMocks(this);
        UserController userController = new UserController(userService);
        when(userService.getAll()).thenReturn(Collections.emptyList());

        String viewName = userController.getAll(model);

        assertEquals("users-list", viewName);
        verify(model).addAttribute(eq("users"), anyList());
        verify(userService).getAll();
    }


    @Test
    void saveUser_ValidUser_RedirectsToUsersPage() {
        MockitoAnnotations.openMocks(this);
        UserController userController = new UserController(userService);
        UserRegisterDto userDto = new UserRegisterDto();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = userController.saveUser(userDto, model, bindingResult);

        assertEquals("redirect:/users", result);
        verify(userService).create(userDto);
        verifyNoInteractions(model);
    }

    @Test
    void saveUser_InvalidUser_ReturnsErrorPage() {
        MockitoAnnotations.openMocks(this);
        UserController userController = new UserController(userService);
        UserRegisterDto userDto = new UserRegisterDto();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = userController.saveUser(userDto, model, bindingResult);

        assertEquals("error-page", result);
        verify(model).addAttribute(eq("error"), any(String.class));
        verifyNoInteractions(userService);
    }


    @Test
    void testGetAllUsers() {
        MockitoAnnotations.openMocks(this);

        List<UserResponseDto> userResponseDtos = List.of(new UserResponseDto(), new UserResponseDto());

        when(userService.getAll()).thenReturn(userResponseDtos);

        List<UserResponseDto> response = userService.getAll();

        assertEquals(userResponseDtos.size(), response.size());

        verify(userService, times(1)).getAll();
    }

    @Test
    void testCreateUser_Success() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository);
        UserRegisterDto request = new UserRegisterDto("Popescu", "Ana", "popesc.ana@example.com", "password123");
        User user = request.toUser(new User());
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        when(userRepository.save(user)).thenReturn(user);

        var response = userService.create(request);

        assertEquals(user.getEmail(), response.getEmail());
        verify(userRepository, times(1)).findAll();
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testCreateUser_UserAlreadyExists() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository);
        UserRegisterDto request = new UserRegisterDto("Popescu", "Ana", "popesc.ana@example.com", "password123");
        List<User> existingUsers = new ArrayList<>();
        existingUsers.add(new User("Popescu", "Ana", "popesc.ana@example.com", "password123"));
        when(userRepository.findAll()).thenReturn(existingUsers);

        var response = userService.create(request);

        assertEquals(null, response);

        verify(userRepository, times(1)).findAll();
        verify(userRepository, never()).save(any(User.class));
    }
}


