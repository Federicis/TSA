package com.example.backend.model.task;

import com.example.backend.model.enumeration.TaskType;
import com.example.backend.model.RoutineModel;
import com.example.backend.model.record.RoutineRecord;
import com.example.backend.model.record.TaskRecord;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public abstract class TaskModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "routine_id")
    private RoutineModel routine;

    @OneToMany
    private List<TaskRecord> records;

    @Transient
    public abstract TaskType getTaskType();
}
