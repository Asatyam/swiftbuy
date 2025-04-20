package com.satyamagrawal.swiftbuy.userservice.dto;

import java.time.LocalDateTime;

public record UserRegistrationResponse(
        Long id,
        String username,
        String firstName,
        String lastName,
        String email,
        LocalDateTime createdAt
) {
}
