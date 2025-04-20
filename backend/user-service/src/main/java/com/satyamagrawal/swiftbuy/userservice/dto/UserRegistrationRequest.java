package com.satyamagrawal.swiftbuy.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public record UserRegistrationRequest(
        String username,
        String firstName,
        String lastName,
        String email,
        String password
) {
}
