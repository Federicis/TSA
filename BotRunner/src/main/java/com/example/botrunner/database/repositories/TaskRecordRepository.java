package com.example.botrunner.database.repositories;

import com.example.botrunner.database.models.record.TaskRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRecordRepository extends JpaRepository<TaskRecord, Long> {

    @Query(value = "SELECT * FROM task_record WHERE task_id = ?1 ORDER BY id DESC LIMIT 1", nativeQuery = true)
    TaskRecord getLastSuccesfulTaskRecord(Long taskId);
}
