package com.satyamagrawal.swiftbuy.userservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record UserRegistrationRequest(
        @NotBlank String username,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Email String email,
        @NotBlank String password
) {
}
