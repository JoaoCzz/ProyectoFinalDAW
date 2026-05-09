package com.joaoczz.backend.service.implementation;

import com.joaoczz.backend.persistence.entity.RoleEntity;
import com.joaoczz.backend.persistence.entity.RoleEnum;
import com.joaoczz.backend.persistence.entity.UserEntity;
import com.joaoczz.backend.persistence.repository.RoleRepository;
import com.joaoczz.backend.persistence.repository.UserRepository;
import com.joaoczz.backend.presentation.advice.ResourceNotFoundException;
import com.joaoczz.backend.presentation.dto.user.UserResponse;
import com.joaoczz.backend.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse getById(Long id) {
        return toResponse(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id)));
    }

    @Override
    public UserResponse getByUsername(String username) {
        return toResponse(userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username)));
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void updateRoles(Long id, Set<String> roles) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        
        Set<RoleEntity> roleEntities = roleRepository.findRoleEntitiesByRoleEnumIn(
                roles.stream().map(RoleEnum::valueOf).collect(Collectors.toSet())
        );
        user.setRoles(roleEntities);
        userRepository.save(user);
    }

    @Override
    public void updatePassword(String username, String oldPassword, String newPassword) {
        UserEntity user = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));
                
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void delete(Long id, String requesterUsername) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        // Solo el propio usuario o un admin puede borrar la cuenta
        if (!user.getUsername().equals(requesterUsername)) {
            throw new IllegalArgumentException("No tienes permiso para eliminar esta cuenta");
        }
        userRepository.deleteById(id);
    }

    private UserResponse toResponse(UserEntity u) {
        List<String> roles = u.getRoles().stream()
                .map(r -> r.getRoleEnum().name())
                .collect(Collectors.toList());
        return new UserResponse(
                u.getId(),
                u.getUsername(),
                u.getName(),
                u.getSurname(),
                u.getEmail(),
                u.getBirthDay(),
                u.getCreatedAt(),
                roles
        );
    }
}
