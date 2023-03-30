package com.example.backend.controller;

<<<<<<< HEAD
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
=======
import org.springframework.http.ResponseEntity;
>>>>>>> ebbac47037a3ec7f996931ab97900c882f84b4cb
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD
import com.example.backend.DTO.Auth.LoginRequest;
import com.example.backend.DTO.Auth.RegisterRequest;
=======
>>>>>>> ebbac47037a3ec7f996931ab97900c882f84b4cb
import com.example.backend.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
<<<<<<< HEAD
        return ResponseEntity.ok(authenticationService.register(request, "localhost:8080"));
=======
        return ResponseEntity.ok(authenticationService.register(request));
>>>>>>> ebbac47037a3ec7f996931ab97900c882f84b4cb
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
<<<<<<< HEAD

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@Param("code") String code) {
        boolean verified = authenticationService.verifyUser(code);

        if (verified) {
            return ResponseEntity.status(HttpStatus.OK).body("");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }
=======
>>>>>>> ebbac47037a3ec7f996931ab97900c882f84b4cb
}
