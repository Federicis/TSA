package com.example.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.example.backend.model.RefreshTokenModel;
import com.example.backend.model.UserModel;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenModel, Long> {
    Optional<RefreshTokenModel> findByToken(String token);

    List<RefreshTokenModel> findAllByUser(UserModel user);

    @Modifying
    int deleteByUser(UserModel user);
}
