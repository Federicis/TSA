package com.example.backend.repository;

import com.example.backend.model.RoutineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoutineRepository extends JpaRepository<RoutineModel, Long> {

    List<RoutineModel> findByBotId(Long botId);

    @Query("SELECT r FROM RoutineModel r JOIN FETCH r.tasks WHERE r.id = :id")
    RoutineModel findByIdWithTasks(Long id);
}
