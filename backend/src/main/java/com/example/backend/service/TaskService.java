package com.example.backend.service;

import com.example.backend.DTO.TaskDTO;
import com.example.backend.model.BotModel;
import com.example.backend.model.task.TaskModel;
import com.example.backend.repository.BotRepository;
import com.example.backend.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final BotRepository botRepository;
    private final JwtService jwtService;

    @Autowired
    public TaskService(TaskRepository taskRepository, BotRepository botRepository, JwtService jwtService) {
        this.taskRepository = taskRepository;
        this.botRepository = botRepository;
        this.jwtService = jwtService;
    }

//
//    public List<TaskModel> getTasks(Long id) {
//        String username = jwtService.getCurrentUserUsername();
//        BotModel bot = botRepository.findById(id).orElseThrow(() -> new IllegalStateException(
//                "bot with id " + id + " does not exist"
//        ));
//        if(bot.getUsername().equals(username))
//            return taskRepository.findByBotId(id).orElse(null);
//        else throw new IllegalStateException("bot with id " + id + " does not belong to user " + username);
//    }
//
//
//    public void addTask(TaskDTO task) {
//        String username = jwtService.getCurrentUserUsername();
//        BotModel bot = botRepository.findById(task.getBotId()).orElseThrow(() -> new IllegalStateException(
//                "bot with id " + task.getBotId() + " does not exist"
//        ));
//        if(!bot.getUsername().equals(username))
//            throw new IllegalStateException("bot with id " + task.getBotId() + " does not belong to user " + username);
//        TaskModel newTask = TaskModel.builder()
//                .type(task.getType())
//                .parameters(String.join(",", task.getParameters()))
//                .botId(task.getBotId())
//                .build();
//        taskRepository.save(newTask);
//    }
//
//    public void deleteTask(Long taskId) {
//        if(!taskBelongsToUser(taskId))
//            throw new IllegalStateException("task with id " + taskId + " does not belong to user " + jwtService.getCurrentUserUsername());
//        taskRepository.deleteById(taskId);
//    }
//
//    @Transactional
//    public void updateTask(TaskModel newTask) {
//        if(!taskBelongsToUser(newTask.getId()))
//            throw new IllegalStateException("task with id " + newTask.getId() + " does not belong to user " + jwtService.getCurrentUserUsername());
//        TaskModel oldTask = taskRepository.findById(newTask.getId()).orElseThrow(() -> new IllegalStateException(
//                "task with id " + newTask.getId() + " does not exist"
//        ));
//        if(!taskBelongsToUser(oldTask.getBotId()))
//            throw new IllegalStateException("task with id " + newTask.getId() + " does not belong to user " + jwtService.getCurrentUserUsername());
//        oldTask.setType(newTask.getType());
//        oldTask.setParameters(newTask.getParameters());
//        oldTask.setBotId(newTask.getBotId());
//    }
//
//    private boolean taskBelongsToUser(Long taskId) {
//        String username = jwtService.getCurrentUserUsername();
//        TaskModel task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalStateException(
//                "task with id " + taskId + " does not exist"
//        ));
//        BotModel bot = botRepository.findById(task.getBotId()).orElseThrow(() -> new IllegalStateException(
//                "bot with id " + task.getBotId() + " does not exist"
//        ));
//        return bot.getUsername().equals(username);
//    }
}
