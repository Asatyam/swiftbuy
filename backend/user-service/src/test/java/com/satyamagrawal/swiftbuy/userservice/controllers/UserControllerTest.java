package com.satyamagrawal.swiftbuy.userservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satyamagrawal.swiftbuy.userservice.dto.UserRegistrationRequest;
import com.satyamagrawal.swiftbuy.userservice.dto.UserRegistrationResponse;
import com.satyamagrawal.swiftbuy.userservice.entity.User;
import com.satyamagrawal.swiftbuy.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {


    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;
    private ObjectMapper mapper;
    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void shouldRegisterUserSuccessfully() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest(
                "testuser",
                "Test",
                "User",
                "testuser123@mail.com",
                "password123"
        );

        UserRegistrationResponse response = new UserRegistrationResponse(
                123L,
                request.username(),
                request.firstName(),
                request.lastName(),
                request.email(),
                LocalDateTime.now()
        );

        when(userService.registerUser(request)).thenReturn(response);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.username").value(response.username()))
                .andExpect(jsonPath("$.firstName").value(response.firstName()))
                .andExpect(jsonPath("$.lastName").value(response.lastName()))
                .andExpect(jsonPath("$.email").value(response.email()));
    }

}
