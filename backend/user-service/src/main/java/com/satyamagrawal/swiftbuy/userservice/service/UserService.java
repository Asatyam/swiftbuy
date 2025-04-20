package com.satyamagrawal.swiftbuy.userservice.service;

import com.satyamagrawal.swiftbuy.userservice.dto.UserRegistrationRequest;
import com.satyamagrawal.swiftbuy.userservice.dto.UserRegistrationResponse;
import com.satyamagrawal.swiftbuy.userservice.entity.User;
import com.satyamagrawal.swiftbuy.userservice.exception.DuplicateResourceException;
import com.satyamagrawal.swiftbuy.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest) {
        if (userRepository.existsByEmail(userRegistrationRequest.email())) {
            throw new DuplicateResourceException("Email " + userRegistrationRequest.email() +" address already in use");
        }
        if (userRepository.existsByUsername(userRegistrationRequest.username())) {
            throw new DuplicateResourceException("Username " + userRegistrationRequest.username() +" already in use");
        }

        User user =  User.builder()
                .email(userRegistrationRequest.email())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .firstName(userRegistrationRequest.firstName())
                .lastName(userRegistrationRequest.lastName())
                .username(userRegistrationRequest.username())
                .password(passwordEncoder.encode(userRegistrationRequest.password()))
                .build();

        User savedUser = userRepository.save(user);
        return userMapToResponse(savedUser);
    }
    public UserRegistrationResponse userMapToResponse(User user) {
        return new UserRegistrationResponse(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }
}
