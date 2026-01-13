package com.joaoczz.backend.presentation.dto.Auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record AuthCreateUserRequest(
       @NotBlank String username,
       @NotBlank  String password,
       @NotBlank  String name,
       @NotBlank  String surname,
       @NotBlank String email,
       @NotBlank LocalDate birthDay,
       @Valid AuthCreateRoleRequest roleRequest

) {
}
