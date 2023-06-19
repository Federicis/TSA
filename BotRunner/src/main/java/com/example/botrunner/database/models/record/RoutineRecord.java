package com.example.botrunner.database.models.record;

import com.example.botrunner.database.models.RoutineModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class RoutineRecord extends Record {
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "routine_id")
    RoutineModel routine;
}
