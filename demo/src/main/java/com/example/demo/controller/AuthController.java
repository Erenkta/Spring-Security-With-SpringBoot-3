package com.example.demo.controller;

import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.LoginRequest;
import com.example.demo.Model.LoginResponse;
import com.example.demo.Security.JwtIssuer;
import com.example.demo.Security.UserPrincipal;
import com.example.demo.Service.AuthService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor //final olan her şeyi constructor oluşturup ekliyor
public class AuthController {

    private final AuthService authService;

    
    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.attemptLogin(request.getEmail(), request.getPassword());

    }
    

}
