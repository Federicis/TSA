package com.example.botrunner.database.repositories;

import com.example.botrunner.database.models.BotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotRepository extends JpaRepository<BotModel, Long> {

}
