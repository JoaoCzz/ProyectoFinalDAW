package com.joaoczz.backend.service.implementation;

import com.joaoczz.backend.persistence.entity.RoleEnum;
import com.joaoczz.backend.persistence.entity.RoleEntity;
import com.joaoczz.backend.persistence.entity.UserEntity;
import com.joaoczz.backend.persistence.repository.RoleRepository;
import com.joaoczz.backend.persistence.repository.UserRepository;
import com.joaoczz.backend.presentation.dto.Auth.AuthCreateUserRequest;
import com.joaoczz.backend.presentation.dto.Auth.AuthLoginRequest;
import com.joaoczz.backend.presentation.dto.Auth.AuthResponse;
import com.joaoczz.backend.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class UserDetailServiceimpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe!"));


        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();


        userEntity.getRoles().forEach(role ->
                authorityList.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleEnum().name()))
        );


        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permission ->
                        authorityList.add(new SimpleGrantedAuthority(permission.getName()))
                );


        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                authorityList
        );
    }

    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();


        Authentication authentication = this.authenticate(username, password);


        SecurityContextHolder.getContext().setAuthentication(authentication);


        String accessToken = jwtUtils.createToken(authentication);


        return new AuthResponse(username, "Usuario autenticado correctamente", accessToken, true);
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Usuario o contraseña inválidos");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest) {
        String username = authCreateUserRequest.username();
        String password = authCreateUserRequest.password();
        List<String> roleRequest = authCreateUserRequest.roleRequest().roleListName();


        Set<RoleEntity> roleEntitySet = roleRepository
                .findRoleEntitiesByRoleEnumIn(roleRequest.stream().map(RoleEnum::valueOf).collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toSet());

        if (roleEntitySet.isEmpty()) {
            throw new IllegalArgumentException("Los roles especificados no existen");
        }


        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(roleEntitySet)
                .name(authCreateUserRequest.name())
                .surname(authCreateUserRequest.surname())
                .email(authCreateUserRequest.email())
                .birthDay(authCreateUserRequest.birthDay())
                .createdAt(LocalDateTime.now())
                .isEnabled(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .build();


        UserEntity userCreated = userRepository.save(userEntity);


        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        userCreated.getRoles().forEach(role ->
                authorityList.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleEnum().name()))
        );
        userCreated.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));


        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getUsername(), userCreated.getPassword(), authorityList);
        String accessToken = jwtUtils.createToken(authentication);


        return new AuthResponse(userCreated.getUsername(), "Usuario creado correctamente", accessToken, true);

    }


}
