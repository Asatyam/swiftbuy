package com.satyamagrawal.swiftbuy.userservice.service;

import com.satyamagrawal.swiftbuy.userservice.dto.UserRegistrationRequest;
import com.satyamagrawal.swiftbuy.userservice.entity.User;
import com.satyamagrawal.swiftbuy.userservice.exception.DuplicateResourceException;
import com.satyamagrawal.swiftbuy.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceRegistrationTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;
    private UserRegistrationRequest request;


    @BeforeEach
    void setUp() {
         request = new UserRegistrationRequest(
                "testuser",
                "Test",
                "User",
                "testuser123@mail.com",
                "password123"
        );
    }

    @Test
    void shouldRegisterUserSuccessfully() {

        when(userRepository.existsByEmail(request.email())).thenReturn(false);
        when(userRepository.existsByUsername(request.username())).thenReturn(false);
        when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");

        User mockedUser = new User();
        mockedUser.setUsername(request.username());
        mockedUser.setPassword("encodedPassword");
        mockedUser.setFirstName(request.firstName());
        mockedUser.setLastName(request.lastName());
        mockedUser.setUpdatedAt(LocalDateTime.now());

        when(userRepository.save(any(User.class))).thenReturn(mockedUser);

        User result = userService.registerUser(request);
        assertNotNull(result,"User should not be null");
        assertEquals(mockedUser.getUsername(), result.getUsername(), "Usernames should match");
        assertEquals(mockedUser.getFirstName(), result.getFirstName(), "First names should match");
        assertEquals(mockedUser.getLastName(), result.getLastName(), "Last names should match");
        assertEquals(mockedUser.getEmail(), result.getEmail(), "Emails should match");
        assertEquals(mockedUser.getPassword(), result.getPassword(), "Passwords should match");

    }

    @Test
    void shouldThrowExceptionWhenEmailExists() {
        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        try {
            userService.registerUser(request);
        } catch (DuplicateResourceException e) {
            assertEquals("Email " + request.email() + " address already in use", e.getMessage());
        }
    }
    @Test
    void shouldThrowExceptionWhenUsernameExists() {
        when(userRepository.existsByEmail(request.email())).thenReturn(false);
        when(userRepository.existsByUsername(request.username())).thenReturn(true);

        try {
            userService.registerUser(request);
        } catch (DuplicateResourceException e) {
            assertEquals("Username " + request.username() + " already in use", e.getMessage());
        }
    }
}
