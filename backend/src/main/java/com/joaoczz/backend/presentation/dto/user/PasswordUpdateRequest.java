package com.joaoczz.backend.presentation.dto.user;

public record PasswordUpdateRequest(String oldPassword, String newPassword) {}
