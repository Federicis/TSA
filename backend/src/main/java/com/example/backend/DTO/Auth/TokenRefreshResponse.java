package com.example.backend.DTO.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TokenRefreshResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String refreshToken;
}
