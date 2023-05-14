package com.example.backend.repository;

import com.example.backend.model.BotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BotRepository extends JpaRepository<BotModel, Long> {

    Optional<BotModel> findBotByName(String name);

    Optional<List<BotModel>> findByUserUsername(String username);
}
