package com.example.backend.DTO;

import com.example.backend.model.enumeration.TaskType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This class will contain all parameters that a task can have
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private TaskType taskType;
    private Long id;
    private List<String> keywords = null;
    private String subreddit = null;
    private String comment = null;
}
