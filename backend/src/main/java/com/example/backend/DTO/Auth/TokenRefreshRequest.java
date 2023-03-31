package com.example.backend.DTO.Auth;

import lombok.Getter;

@Getter
public class TokenRefreshRequest {
    private String refreshToken;
}
