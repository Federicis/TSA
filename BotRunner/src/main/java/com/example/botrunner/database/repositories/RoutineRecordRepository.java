package com.example.botrunner.database.repositories;

import com.example.botrunner.database.models.record.RoutineRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineRecordRepository extends JpaRepository<RoutineRecord, Long> {
    @Query(value = "SELECT * FROM routine_record WHERE routine_id = ?1 ORDER BY timestamp DESC LIMIT 1", nativeQuery = true)
    RoutineRecord getLastSuccesfulRoutineRecord(Long routineId);
}
