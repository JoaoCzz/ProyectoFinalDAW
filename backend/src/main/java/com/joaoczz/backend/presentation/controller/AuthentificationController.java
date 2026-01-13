package com.joaoczz.backend.presentation.controller;

import com.joaoczz.backend.presentation.dto.Auth.AuthCreateUserRequest;
import com.joaoczz.backend.presentation.dto.Auth.AuthLoginRequest;
import com.joaoczz.backend.presentation.dto.Auth.AuthResponse;
import com.joaoczz.backend.service.implementation.UserDetailServiceimpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthentificationController {
    @Autowired
    private UserDetailServiceimpl userDetailServiceimpl;

    @PostMapping("/sing-up")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest authCreateUser){
        return new ResponseEntity<>(this.userDetailServiceimpl.createUser(authCreateUser), HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<>(this.userDetailServiceimpl.loginUser(userRequest), HttpStatus.OK);
    }
}
