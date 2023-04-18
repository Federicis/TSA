package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class TaskModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //FIXME maybe find another way to ensure that the task list remains ordered.
    @Column(name = "`index`")
    private Long index;
    @ManyToOne
    private RoutineModel routine;
}
