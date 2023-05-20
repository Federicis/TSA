package com.example.botrunner.executor;

import ca.arnah.reddit4j.RedditUserClient;
import com.example.botrunner.database.models.RoutineModel;
import com.example.botrunner.database.models.task.TaskModel;
import com.example.botrunner.executor.taskExecutor.TaskExecutor;
import com.example.botrunner.executor.taskExecutor.TaskExecutorFactory;

import java.util.ArrayList;
import java.util.List;

public class RoutineExecutor {
    private final RunningData runningData;
    private final RoutineModel routine;
    private final RedditUserClient redditUserClient;
    private final List<TaskExecutor> taskExecutors = new ArrayList<>();


    public RoutineExecutor(RoutineModel routine, RedditUserClient redditUserClient) {
        this.routine = routine;
        this.runningData = new RunningData();
        this.redditUserClient = redditUserClient;
    }
    public RoutineExecutor(RunningData runningData, RoutineModel routine, RedditUserClient redditUserClient) {
        this.runningData = runningData;
        this.routine = routine;
        this.redditUserClient = redditUserClient;
    }

    public void execute() {
        prepare();
        for (TaskExecutor taskExecutor : taskExecutors) {
            taskExecutor.execute(runningData, redditUserClient);
        }
    }

    private void prepare(){
        for(TaskModel task : routine.getTasks()){
            taskExecutors.add(TaskExecutorFactory.createTaskExecutor(task));
        }
    }

}
