package com.example.backend.DTO.Auth;

import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;
}
