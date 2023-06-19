package com.example.backend.controller;

import com.example.backend.DTO.Auth.*;
import com.example.backend.service.AuthenticationService;
import com.example.backend.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    private static final String clientURL = "${tsa.clientUrl}";

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
                    .secure(true)
                    .sameSite("none")
                    .build();

            return ResponseEntity.status(HttpStatus.OK)
                    .header("Set-Cookie", cookie.toString())
                    .body(loginResponse);

        }

        return ResponseEntity.status(authResponse.getStatus()).body(null);
    }

    @CrossOrigin(allowCredentials = "true", origins = clientURL)
    @PostMapping("/refreshtoken")
    public ResponseEntity<TokenResponse> refreshToken(@CookieValue("refreshToken") String token,
                                                      @RequestBody TokenRefreshRequest request) {
        System.out.println("token");
        AuthenticationResponse authResponse = jwtService.refreshToken(token);

        TokenResponse tokenResponse = new TokenResponse(authResponse.getAccessToken());
        ResponseCookie cookie = ResponseCookie.from("refreshToken", authResponse.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("none")
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
