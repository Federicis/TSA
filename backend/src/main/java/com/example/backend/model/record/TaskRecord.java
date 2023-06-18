package com.example.backend.model.record;

import com.example.backend.model.task.TaskModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TaskRecord extends Record {
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "task_id")
    TaskModel task;
}
