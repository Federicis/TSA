package com.example.botrunner.database.models.task;

import com.example.botrunner.database.models.enumerations.TaskType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("COMMENT")
public class CommentTaskModel extends TaskModel {
    private String comment;

    @Transient
    @Override
    public TaskType getTaskType() {
        return TaskType.COMMENT;
    }
}
