package com.example.backend.Util.Mapper;

import com.example.backend.DTO.TaskDTO;
import com.example.backend.model.task.CommentTaskModel;
import com.example.backend.model.task.FindTaskModel;
import com.example.backend.model.task.TaskModel;
import org.mapstruct.ObjectFactory;

public class TaskFactory {
    @ObjectFactory
    public static TaskModel createTask(TaskDTO taskDTO)
    {
        return switch (taskDTO.getTaskType()) {
            case FIND -> new FindTaskModel();
            case COMMENT -> new CommentTaskModel();
            default -> null;
        };
    }
}
