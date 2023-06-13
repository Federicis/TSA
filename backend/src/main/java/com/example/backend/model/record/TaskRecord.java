package com.example.backend.model.record;

import com.example.backend.model.task.TaskModel;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TaskRecord extends Record {
    @ManyToOne
    @JoinColumn(name = "task_id")
    TaskModel task;
}
