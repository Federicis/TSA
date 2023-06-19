package com.example.botrunner.executor;

import ca.arnah.reddit4j.RedditUserClient;
import com.example.botrunner.database.models.RoutineModel;
import com.example.botrunner.database.models.record.RoutineRecord;
import com.example.botrunner.database.models.task.TaskModel;
import com.example.botrunner.executor.taskExecutor.TaskExecutor;
import com.example.botrunner.executor.taskExecutor.TaskExecutorFactory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RoutineExecutor implements Runnable {
    private final RunningData runningData;
    private final RoutineModel routine;
    private final RedditUserClient redditUserClient;
    private final List<TaskExecutor> taskExecutors = new ArrayList<>();
    private final RoutineRecord record = new RoutineRecord();


    public RoutineExecutor(RoutineModel routine, RedditUserClient redditUserClient) {
        this.routine = routine;
        this.runningData = new RunningData();
        this.redditUserClient = redditUserClient;
        record.setRoutine(routine);
    }

    public RoutineExecutor(RunningData runningData, RoutineModel routine, RedditUserClient redditUserClient) {
        this.runningData = runningData;
        this.routine = routine;
        this.redditUserClient = redditUserClient;
        record.setRoutine(routine);
    }

    public void run() {
        prepare();
        try {
            for (TaskExecutor taskExecutor : taskExecutors) {
                taskExecutor.execute(runningData, redditUserClient);
            }
            record.setWasSuccessful(true);
            record.setMessage("Routine executed successfully");
        } catch (Exception e) {
            record.setWasSuccessful(true);
            record.setMessage(e.getMessage());
        }
    }

    private void prepare() {
        for (TaskModel task : routine.getTasks()) {
            taskExecutors.add(TaskExecutorFactory.createTaskExecutor(task));
        }
    }

}
