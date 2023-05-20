package com.example.botrunner.executor.taskExecutor;


import com.example.botrunner.database.models.task.CommentTaskModel;
import com.example.botrunner.database.models.task.FindTaskModel;
import com.example.botrunner.database.models.task.TaskModel;

public class TaskExecutorFactory {
    public static TaskExecutor createTaskExecutor(TaskModel task) {

        // can't use switch because it only works with primitives
        if (task.getClass().equals(FindTaskModel.class)) {
            return new FindTaskExecutor((FindTaskModel) task);
        } else if (task.getClass().equals(CommentTaskModel.class)) {
            return new CommentTaskExecutor((CommentTaskModel) task);
        }
        throw new IllegalArgumentException("Unknown class type: " + task.getClass());
    }
}
