package com.example.backend.service;

import com.example.backend.DTO.TaskDTO;
import com.example.backend.model.TaskModel;
import com.example.backend.repository.BotRepository;
import com.example.backend.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final BotRepository botRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, BotRepository botRepository) {
        this.taskRepository = taskRepository;
        this.botRepository = botRepository;
    }


    public List<TaskModel> getTasks(Long id) {
        if (botRepository.existsById(id)) {
            return taskRepository.findTasksByBotId(id).orElse(List.of());
        } else throw new IllegalStateException("bot with id " + id + " does not exist");
    }


    public void addTask(TaskDTO task) {
        TaskModel newTask = TaskModel.builder()
                .type(task.getType())
                .parameters(String.join(",", task.getParameters()))
                .botId(task.getBotId())
                .build();
        taskRepository.save(newTask);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Transactional
    public void updateTask(TaskModel newTask) {
        Long taskId = newTask.getId();
        TaskModel oldTask = taskRepository.findById(taskId).orElseThrow(() -> new IllegalStateException(
                "task with id " + taskId + " does not exist"
        ));
        oldTask.setType(newTask.getType());
        oldTask.setParameters(newTask.getParameters());
    }
}
