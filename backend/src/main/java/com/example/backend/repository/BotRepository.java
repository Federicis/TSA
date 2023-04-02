package com.example.backend.repository;

import com.example.backend.model.BotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BotRepository extends JpaRepository<BotModel, Long> {

    Optional<BotModel> findBotByName(String name);
}
