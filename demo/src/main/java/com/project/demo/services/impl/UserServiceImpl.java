package com.project.demo.services.impl;

import com.project.demo.controllers.OrderController;
import com.project.demo.dtos.user.LoginMessage;
import com.project.demo.dtos.user.UserLoginDto;
import com.project.demo.dtos.user.UserRegisterDto;
import com.project.demo.dtos.user.UserResponseDto;
import com.project.demo.repositories.UserRepository;
import com.project.demo.models.User;
import com.project.demo.services.UserService;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserResponseDto create(UserRegisterDto request) {
        var users = userRepository.findAll();
        log.info("Existing users: " + users.toString());
        log.info("Email to create: " + request.getEmail());

        if (users.stream().noneMatch(x -> Objects.equals(Objects.toString(x.getEmail(), null), request.getEmail()))) {
            User user = request.toUser(new User());
            var createdUser = userRepository.save(user);
            return new UserResponseDto().fromUser(createdUser);
        }
        return null;
    }

    @Override
    public LoginMessage login(UserLoginDto request){
        String msg = "";
        User user = userRepository.findByEmail(request.getEmail());
        if (user != null) {
            String password = request.getPassword();
            String existingPassword = user.getPassword();
            boolean isPwdRight = Objects.equals(password, existingPassword);
            if (isPwdRight) {
                Optional<User> existingUser = userRepository.findOneByEmailAndPassword(request.getEmail(), existingPassword);
                if (existingUser.isPresent()) {
                    return new LoginMessage("Login Success", true);
                } else {
                    return new LoginMessage("Login Failed", false);
                }
            } else {
                return new LoginMessage("password Not Match", false);
            }
        }else {
            return new LoginMessage("Email not exits", false);
        }
    }

    @Override
    public List<UserResponseDto> getAll(){
        var users = userRepository.findAll();
        return users.stream().map(x -> new UserResponseDto().fromUser(x))
                .collect(Collectors.toList());
    }

}