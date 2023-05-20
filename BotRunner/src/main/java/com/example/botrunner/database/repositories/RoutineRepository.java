package com.example.botrunner.database.repositories;

import com.example.botrunner.database.models.RoutineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoutineRepository extends JpaRepository<RoutineModel, Long> {

}
