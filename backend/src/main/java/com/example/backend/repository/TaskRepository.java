package com.example.backend.repository;

import com.example.backend.model.task.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Long> {
    //Optional<List<TaskModel>> findByBotId(Long botId);
}
