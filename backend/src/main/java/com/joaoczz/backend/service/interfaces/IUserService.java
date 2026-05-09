package com.joaoczz.backend.service.interfaces;

import com.joaoczz.backend.presentation.dto.user.UserResponse;

import java.util.List;
import java.util.Set;

public interface IUserService {
    UserResponse getById(Long id);
    UserResponse getByUsername(String username);
    List<UserResponse> getAll();
    void updateRoles(Long id, Set<String> roles);
    void updatePassword(String username, String oldPassword, String newPassword);
    void delete(Long id, String username);
}
