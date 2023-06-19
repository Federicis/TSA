package com.example.botrunner.database.models;

import com.example.botrunner.database.models.enumerations.Interval;
import com.example.botrunner.database.models.task.TaskModel;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderColumn(name = "task_index")
    private List<TaskModel> tasks;

    @Column(name = "`repeatable`")
    private boolean repeatable;

    @Column(name = "`interval`")
    private Interval interval;


}
