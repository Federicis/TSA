package com.example.backend.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.DTO.Auth.AuthenticationResponse;
import com.example.backend.DTO.Auth.LoginRequest;
import com.example.backend.DTO.Auth.RegisterRequest;
import com.example.backend.DTO.Auth.TokenRefreshRequest;
import com.example.backend.service.AuthenticationService;
import com.example.backend.service.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        AuthenticationResponse registerResponse = authenticationService.register(request);

        return ResponseEntity.status(registerResponse.getStatus()).body(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {

        AuthenticationResponse loginResponse = authenticationService.login(request);

        return ResponseEntity.status(loginResponse.getStatus()).body(loginResponse);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
        String token = request.getRefreshToken();
        System.out.println(token);
        AuthenticationResponse response = jwtService.refreshToken(token);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@Param("code") String code) {
        boolean verified = authenticationService.verifyUser(code);

        if (!verified) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }

        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
