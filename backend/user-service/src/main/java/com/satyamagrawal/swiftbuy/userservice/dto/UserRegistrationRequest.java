package com.satyamagrawal.swiftbuy.userservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record UserRegistrationRequest(
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "firstName is required")
        String firstName,
        @NotBlank(message = "lastName is required")
        String lastName,
        @NotBlank(message = "email is required")
        @Email(message = "Email should be valid")
        String email,
        @NotBlank(message = "password is required")
        @NotBlank String password
) {
}
