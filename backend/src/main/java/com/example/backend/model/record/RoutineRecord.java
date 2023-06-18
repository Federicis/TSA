package com.example.backend.model.record;

import com.example.backend.model.RoutineModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RoutineRecord extends Record{
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "routine_id")
    RoutineModel routine;
}
