package com.example.backend.repository;

import com.example.backend.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TaskRepository  extends JpaRepository<TaskModel, Long> {
    @Query("SELECT t FROM TaskModel t WHERE t.botId = ?1")
    Optional<List<TaskModel>> findTasksByBotId(Long botId);
}
