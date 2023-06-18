package com.example.botrunner.executor;

import com.example.botrunner.database.models.RoutineModel;
import com.example.botrunner.database.services.BotService;
import com.example.botrunner.database.services.RecordService;
import com.example.botrunner.database.services.RoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoutineSelector {

    private final BotService botService;

    private final RoutineService routineService;

    private final RecordService recordService;

    public List<RoutineModel> getRoutinesAwaitingExecution() {

        List<RoutineModel> routines = routineService.getAllRoutines();
        // TODO: filter out the routines based on the last execution time (implement taskResults first)
        List<RoutineModel> routinesSelected = new ArrayList<>();
        for(RoutineModel routine: routines)
        {
            Timestamp lastRun = new Timestamp(0);
            var lastRoutineRecord = recordService.getLastSuccesfulRoutineRecord(routine.getId());
            if(lastRoutineRecord.isPresent())
            {
                lastRun = lastRoutineRecord.get().getTimestamp();
            }

//            set a variabile required time depending on the interval
            long reqTime = 0;
            switch (routine.getInterval())
            {
                case DAILY:
                    reqTime = 24*60*60*1000;
                    break;
                case HOURLY:
                    reqTime = 60*60*1000;
                    break;
                case ASAP: //1 minute
                    reqTime = 60*1000;
                    break;
            }
            if (System.currentTimeMillis() - lastRun.getTime() > reqTime)
            {
                routinesSelected.add(routine);
            }
        }
        return routinesSelected;
    }
}
