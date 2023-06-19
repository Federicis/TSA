package com.example.backend.Util.Mapper;

import com.example.backend.DTO.TaskDTO;
import com.example.backend.model.task.CommentTaskModel;
import com.example.backend.model.task.FindTaskModel;
import com.example.backend.model.task.TaskModel;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {TaskFactory.class})
public interface TaskMapper {
    FindTaskModel toFindTaskModel(TaskDTO taskDTO);

    CommentTaskModel toCommentTaskModel(TaskDTO taskDTO);

    //@Mapping(target = "taskType", defaultValue = "FIND")
    TaskDTO toTaskDTO(FindTaskModel findTaskModel);

    //@Mapping(target = "taskType", defaultValue = "COMMENT")
    TaskDTO toTaskDTO(CommentTaskModel commentTaskModel);

    @Named("toTask")
    default TaskModel toTask(TaskDTO taskDTO) {
        return switch (taskDTO.getTaskType()) {
            case FIND -> toFindTaskModel(taskDTO);
            case COMMENT -> toCommentTaskModel(taskDTO);
            default -> null;
        };
    }

    default TaskDTO toTaskDTO(TaskModel taskModel) {
        if (taskModel.getClass() == FindTaskModel.class)
            return toTaskDTO((FindTaskModel) taskModel);
        else if (taskModel.getClass() == CommentTaskModel.class)
            return toTaskDTO((CommentTaskModel) taskModel);
        else
            return null;
    }
}
