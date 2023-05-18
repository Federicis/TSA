package com.example.backend.service;

import com.example.backend.model.RoutineModel;
import com.example.backend.repository.RoutineRepository;
import com.example.backend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutineService {
    private final RoutineRepository routineRepository;
    private final TaskRepository taskRepository;
    private final JwtService jwtService;
    private final BotService botService;

    public List<RoutineModel> getRoutinesOfTheBot(Long botId)
    {
        if(!botService.getBotById(botId).getUser().getUsername().equals(jwtService.getCurrentUserUsername()) && !jwtService.isAdmin())
            throw new IllegalAccessError("this bot does not belong to the user");

        return routineRepository.findByBotId(botId);
    }
    public RoutineModel getRoutineById(Long routineId)
    {
        if(!routineRepository.existsById(routineId))
            throw new IllegalStateException("this routine does not exist");
        if(!routineRepository.findById(routineId).get().getBot().getUser().getUsername().equals(jwtService.getCurrentUserUsername()) && !jwtService.isAdmin())
            throw new IllegalAccessError("this routine does not belong to the user");

        return routineRepository.findById(routineId).get();
    }

    public RoutineModel AddNewRoutine(RoutineModel routine)
    {
        if(!botService.getBotById(routine.getBot().getId()).getUser().getUsername().equals(jwtService.getCurrentUserUsername()) && !jwtService.isAdmin())
            throw new IllegalAccessError("this bot does not belong to the user");

        return routineRepository.save(routine);
    }

    public void DeleteRoutine(Long id)
    {
        if(!routineRepository.existsById(id))
            throw new IllegalStateException("this routine does not exist");
        if(!routineRepository.findById(id).get().getBot().getUser().getUsername().equals(jwtService.getCurrentUserUsername()) && !jwtService.isAdmin())
            throw new IllegalAccessError("this routine does not belong to the user");

        routineRepository.deleteById(id);
    }

    public void UpdateRoutine(RoutineModel routine)
    {
        if(!routineRepository.existsById(routine.getId()))
            throw new IllegalStateException("this routine does not exist");
        if(!routineRepository.findById(routine.getId()).get().getBot().getUser().getUsername().equals(jwtService.getCurrentUserUsername()) && !jwtService.isAdmin())
            throw new IllegalAccessError("this routine does not belong to the user");

        routineRepository.save(routine);
    }

}
