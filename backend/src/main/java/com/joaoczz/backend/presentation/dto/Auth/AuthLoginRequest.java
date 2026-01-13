package com.joaoczz.backend.presentation.dto.Auth;


import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(@NotBlank String username, @NotBlank String password) {
}
