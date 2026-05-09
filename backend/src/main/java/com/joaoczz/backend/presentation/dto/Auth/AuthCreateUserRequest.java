package com.joaoczz.backend.presentation.dto.Auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AuthCreateUserRequest(
       @NotBlank String username,
       @NotBlank  String password,
       @NotBlank  String name,
       @NotBlank  String surname,
       @NotBlank String email,
       @NotNull LocalDate birthDay,
       @Valid AuthCreateRoleRequest roleRequest

) {
}
