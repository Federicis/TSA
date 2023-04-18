package com.example.backend.model;

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
    private List<TaskModel> tasks;

    @Column(name = "`repeatable`")
    private boolean repeatable;

    @Column(name = "`interval`")
    private Interval interval;

}
