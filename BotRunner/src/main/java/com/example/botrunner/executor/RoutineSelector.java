package com.example.botrunner.executor;

import com.example.botrunner.database.models.RoutineModel;
import com.example.botrunner.database.services.BotService;
import com.example.botrunner.database.services.RoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoutineSelector {

    private final BotService botService;

    private final RoutineService routineService;

    public List<RoutineModel> getRoutinesAwaitingExecution() {

        List<RoutineModel> routines = routineService.getAllRoutines();
        // TODO: filter out the routines based on the last execution time (implement taskResults first)
        //System.out.println(routines);
        return routines;
    }
}
