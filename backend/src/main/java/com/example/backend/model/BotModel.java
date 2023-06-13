package com.example.backend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bots")
public class BotModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserModel user;
    private String redditUsername;
    private String password;
    private String clientId;
    private String clientSecret;
    @OneToMany(mappedBy = "bot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoutineModel> routines;
}
