package com.satyamagrawal.swiftbuy.userservice.controllers;

import com.satyamagrawal.swiftbuy.userservice.dto.UserRegistrationRequest;
import com.satyamagrawal.swiftbuy.userservice.dto.UserRegistrationResponse;
import com.satyamagrawal.swiftbuy.userservice.entity.User;
import com.satyamagrawal.swiftbuy.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid UserRegistrationRequest request) throws URISyntaxException {

        UserRegistrationResponse userRegistrationResponse = userService.registerUser(request);
        return ResponseEntity.created(new URI("/api/users/" + userRegistrationResponse.id())).body(userRegistrationResponse);
    }

}
