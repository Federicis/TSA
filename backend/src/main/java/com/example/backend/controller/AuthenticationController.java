package com.example.backend.controller;

import java.net.http.HttpHeaders;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.DTO.Auth.AuthenticationResponse;
import com.example.backend.DTO.Auth.LoginRequest;
import com.example.backend.DTO.Auth.TokenResponse;
import com.example.backend.DTO.Auth.RegisterRequest;
import com.example.backend.DTO.Auth.TokenRefreshRequest;
import com.example.backend.service.AuthenticationService;
import com.example.backend.service.JwtService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        AuthenticationResponse registerResponse = authenticationService.register(request);

        return ResponseEntity.status(registerResponse.getStatus()).body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {

        AuthenticationResponse authResponse = authenticationService.login(request);

        if (authResponse.getStatus() == HttpStatus.OK) {
            TokenResponse loginResponse = new TokenResponse(authResponse.getAccessToken());

            ResponseCookie cookie = ResponseCookie.from("refreshToken", authResponse.getRefreshToken())
                    .httpOnly(true)
                    .build();

            return ResponseEntity.status(HttpStatus.OK)
                    .header("Set-Cookie", cookie.toString())
                    .body(loginResponse);

        }

        return ResponseEntity.status(authResponse.getStatus()).body(null);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
        String token = request.getRefreshToken();
        AuthenticationResponse authResponse = jwtService.refreshToken(token);

        TokenResponse tokenResponse = new TokenResponse(authResponse.getAccessToken());
        ResponseCookie cookie = ResponseCookie.from("refreshToken", authResponse.getRefreshToken())
                .httpOnly(true)
                .build();

        return ResponseEntity.status(authResponse.getStatus())
                .header("Set-Cookie", cookie.toString())
                .body(tokenResponse);
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
