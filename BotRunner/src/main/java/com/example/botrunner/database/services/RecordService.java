package com.example.botrunner.database.services;

import com.example.botrunner.database.models.record.RoutineRecord;
import com.example.botrunner.database.models.record.TaskRecord;
import com.example.botrunner.database.repositories.RoutineRecordRepository;
import com.example.botrunner.database.repositories.TaskRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordService {
    private final RoutineRecordRepository routineRecordRepository;
    private final TaskRecordRepository taskRecordRepository;

    public void deleteAllRecords() {
        routineRecordRepository.deleteAll();
        taskRecordRepository.deleteAll();
    }

    public void addTaskRecord(TaskRecord taskRecord) {
        taskRecordRepository.save(taskRecord);
    }

    public void addRoutineRecord(RoutineRecord routineRecord) {
        routineRecordRepository.save(routineRecord);
    }

    public Optional<RoutineRecord> getLastSuccesfulRoutineRecord(Long routineId) {
        return Optional.ofNullable(routineRecordRepository.getLastSuccesfulRoutineRecord(routineId));
    }

    public Optional<TaskRecord> getLastSuccesfulTaskRecord(Long taskId) {
        return Optional.ofNullable(taskRecordRepository.getLastSuccesfulTaskRecord(taskId));
    }
}
