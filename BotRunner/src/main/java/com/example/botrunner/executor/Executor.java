package com.example.botrunner.executor;

import ca.arnah.reddit4j.RedditUserClient;
import ca.arnah.reddit4j.config.RedditClientConfig;
import ca.arnah.reddit4j.config.ScriptClientConfig;
import ca.arnah.reddit4j.objects.app.script.Credentials;
import ca.arnah.reddit4j.objects.app.script.PersonalUseScript;
import ca.arnah.reddit4j.objects.app.script.UserAgent;
import com.example.botrunner.database.models.BotModel;
import com.example.botrunner.database.models.record.RoutineRecord;
import com.example.botrunner.database.services.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

@Component
@RequiredArgsConstructor
public class Executor {

    private final RoutineSelector routineSelector;
    private final RecordService recordService;

    public void execute() {
        while (true) {
            List<Pair<RoutineExecutor, Thread>> routineExecutors = new ArrayList<>();
            for (var routine : routineSelector.getRoutinesAwaitingExecution()) {
                RunningData runningData = new RunningData();
                var lastRecord = recordService.getLastSuccesfulRoutineRecord(routine.getId());
                Timestamp lastRun = new Timestamp(0);
                if (lastRecord.isPresent())
                    lastRun = lastRecord.get().getTimestamp();
                runningData.setLastRun(lastRun);
                RoutineExecutor routineExecutor = new RoutineExecutor(runningData, routine, getUserClient(routine.getBot()));
                Thread thread = new Thread(routineExecutor);
                thread.start();
                routineExecutors.add(Pair.of(routineExecutor, thread));
            }
            // join threads
            for (var routineExecutorThreadPair : routineExecutors) {
                try {
                    routineExecutorThreadPair.getSecond().join();
                    reportData(routineExecutorThreadPair.getFirst().getRecord());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void reportData(RoutineRecord record) {
        recordService.addRoutineRecord(record);
    }

    private RedditUserClient getUserClient(BotModel bot) {

        RedditClientConfig redditClientConfig = new ScriptClientConfig(
                new PersonalUseScript(bot.getClientId(), bot.getClientSecret()),
                new UserAgent("TSAA", "1.0", "tsaaaaaaaa"),
                new Credentials(bot.getRedditUsername(), bot.getPassword())
        );
        return new RedditUserClient(redditClientConfig);
    }

}
