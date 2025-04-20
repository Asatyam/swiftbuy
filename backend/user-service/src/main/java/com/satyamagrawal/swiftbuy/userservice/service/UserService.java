package com.satyamagrawal.swiftbuy.userservice.service;

import com.satyamagrawal.swiftbuy.userservice.dto.UserRegistrationRequest;
import com.satyamagrawal.swiftbuy.userservice.entity.User;
import com.satyamagrawal.swiftbuy.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserRegistrationRequest userRegistrationRequest) {
        if (userRepository.existsByEmail(userRegistrationRequest.email())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (userRepository.existsByUsername(userRegistrationRequest.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(userRegistrationRequest.username());
        user.setFirstName(userRegistrationRequest.firstName());
        user.setLastName(userRegistrationRequest.lastName());
        user.setPassword(passwordEncoder.encode(userRegistrationRequest.password())); // This should be the encoded password
        return userRepository.save(user);
    }
}
