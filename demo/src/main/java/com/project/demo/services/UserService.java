package com.project.demo.services;
import com.project.demo.dtos.user.LoginMessage;
import com.project.demo.dtos.user.UserLoginDto;
import com.project.demo.dtos.user.UserRegisterDto;
import com.project.demo.dtos.user.UserResponseDto;
import com.project.demo.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    public Optional<User> getUserById(Integer userId);
    public User saveUser(User user);
    public UserResponseDto create(UserRegisterDto request);
    public LoginMessage login(UserLoginDto request);
    public List<UserResponseDto> getAll();
}
