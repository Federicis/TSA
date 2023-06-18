package com.example.backend.DTO;

import com.example.backend.model.enumeration.Interval;
import lombok.Data;

import java.util.List;
@Data
public class RoutineDTO {
    public Long id;
    private String name;
    private List<TaskDTO> tasks;
    private boolean repeatable;
    private Interval interval;

    private Long botId;
}
