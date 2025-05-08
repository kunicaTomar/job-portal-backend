// controller/AuthController.java
package com.job_portal_backend.controllers;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.job_portal_backend.dto.AuthRequest;
import com.job_portal_backend.dto.AuthResponse;
import com.job_portal_backend.dto.RegisterRequest;
import com.job_portal_backend.services.AuthService;



@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        System.out.println("register me");
        String message = authService.register(registerRequest);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest loginDto){
        String token = authService.login(loginDto);

        AuthResponse jwtAuthResponse = new AuthResponse();
        jwtAuthResponse.setToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }
}
