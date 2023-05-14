package com.example.backend.model.task;

import com.example.backend.DTO.TaskType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;

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
