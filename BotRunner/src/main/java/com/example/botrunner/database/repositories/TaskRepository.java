package com.example.botrunner.database.repositories;

import com.example.botrunner.database.models.task.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Long> {

}
