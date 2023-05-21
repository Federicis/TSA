package com.example.backend.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BotDTO {
    private Long id;
    private String name;
    private String description;
    private String redditUsername;
    private String password;
    private String clientId;
    private String clientSecret;
}
