package com.example.backend.repository;

import com.example.backend.model.BotModel;
import com.example.backend.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BotRepository extends JpaRepository<BotModel, Long> {

    Optional<List<BotModel>> findByUser(@Param("user") UserModel user);
}
