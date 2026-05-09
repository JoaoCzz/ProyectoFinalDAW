package com.joaoczz.backend.presentation.controller;

import com.joaoczz.backend.presentation.dto.user.PasswordUpdateRequest;
import com.joaoczz.backend.presentation.dto.user.RoleUpdateRequest;
import com.joaoczz.backend.presentation.dto.user.UserResponse;
import com.joaoczz.backend.service.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Gestión de usuarios")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Obtener perfil de usuario por ID")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/me")
    @Operation(summary = "Obtener mi perfil", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<UserResponse> getMe(@AuthenticationPrincipal String username) {
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @GetMapping
    @Operation(summary = "Listar todos los usuarios (Solo ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PatchMapping("/{id}/roles")
    @Operation(summary = "Actualizar roles de un usuario (Solo ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateRoles(@PathVariable Long id, @RequestBody RoleUpdateRequest request) {
        userService.updateRoles(id, request.roles());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/me/password")
    @Operation(summary = "Actualizar mi contraseña", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> updatePassword(
            @AuthenticationPrincipal String username,
            @RequestBody PasswordUpdateRequest request) {
        userService.updatePassword(username, request.oldPassword(), request.newPassword());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar mi cuenta", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal String username) {
        userService.delete(id, username);
        return ResponseEntity.noContent().build();
    }
}
