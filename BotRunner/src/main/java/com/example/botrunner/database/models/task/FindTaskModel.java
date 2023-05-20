package com.example.botrunner.database.models.task;

import com.example.botrunner.database.models.enumerations.TaskType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("FIND")
public class FindTaskModel extends TaskModel {
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> keywords;

    private String subreddit;

    @Override
    public TaskType getTaskType() {
        return TaskType.FIND;
    }
}
