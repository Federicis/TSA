package com.example.backend.model;

import com.example.backend.model.task.TaskModel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class RoutineModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @OrderColumn(name="task_index")
    private List<TaskModel> tasks;

    @Column(name = "`repeatable`")
    private boolean repeatable;

    @Column(name = "`interval`")
    private Interval interval;

}
