package com.example.backend.controller;

import com.example.backend.DTO.RoutineDTO;
import com.example.backend.Util.Mapper.RoutineMapper;
import com.example.backend.model.RoutineModel;
import com.example.backend.service.RoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/routines")
public class RoutineController {
    private final RoutineService routineService;

    private final RoutineMapper routineMapper;

    @GetMapping(path = "/{botId}")
    public List<RoutineDTO> getTasks(@PathVariable("botId") Long botId) {
        var routinesOfTheBot = routineService.getRoutinesOfTheBot(botId);
        return routinesOfTheBot.stream().map(routineMapper::toRoutineDTO).toList();
    }

    @PostMapping
    public RoutineDTO addTask(@RequestBody RoutineDTO routineDTO) {
        RoutineModel routine = routineMapper.toRoutineModel(routineDTO);
        return routineMapper.toRoutineDTO(routineService.AddNewRoutine(routine));
    }

    @DeleteMapping(path = "{routineId}")
    public void deleteTask(@PathVariable("routineId") Long routineId) {
        routineService.DeleteRoutine(routineId);
    }

    @PutMapping
    public RoutineDTO updateTask(@RequestBody RoutineDTO routineDTO) {
        RoutineModel routine = routineMapper.toRoutineModel(routineDTO);
        return routineMapper.toRoutineDTO(routineService.AddNewRoutine(routine));

    }


}
