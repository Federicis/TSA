package com.example.backend.repository;

import com.example.backend.model.RefreshTokenModel;
import com.example.backend.model.UserModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenModel, Long> {
    Optional<RefreshTokenModel> findByToken(String token);

    List<RefreshTokenModel> findAllByUser(UserModel user);

    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshTokenModel rt WHERE rt.user = (SELECT u FROM UserModel u WHERE u.username = :username)")
    void deleteAllByUsername(@Param("username") String username);
}
