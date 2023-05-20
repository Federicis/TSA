package com.example.botrunner.database.services;

import com.example.botrunner.database.models.RoutineModel;
import com.example.botrunner.database.repositories.RoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutineService {
    private final RoutineRepository routineRepository;

    public List<RoutineModel> getAllRoutines() {
        return routineRepository.findAll();
    }

    public RoutineModel getRoutineById(Long id) {
        return routineRepository.findById(id).orElse(null);
    }
}
