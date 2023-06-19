package com.example.botrunner.database.models.record;

import com.example.botrunner.database.models.task.TaskModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class TaskRecord extends Record {
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "task_id")
    TaskModel task;
}
