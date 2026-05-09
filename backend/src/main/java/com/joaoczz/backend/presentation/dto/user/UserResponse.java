package com.joaoczz.backend.presentation.dto.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record UserResponse(
        Long id,
        String username,
        String name,
        String surname,
        String email,
        LocalDate birthDay,
        LocalDateTime createdAt,
        List<String> roles
) {}
