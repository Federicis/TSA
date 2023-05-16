package com.example.backend.model;

import com.example.backend.model.enumeration.Interval;
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

    @ManyToOne
    @JoinColumn(name = "bot_id")
    private BotModel bot;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name="task_index")
    private List<TaskModel> tasks;

    @Column(name = "`repeatable`")
    private boolean repeatable;

    @Column(name = "`interval`")
    private Interval interval;


}
