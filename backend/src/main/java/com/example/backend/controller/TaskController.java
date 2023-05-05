package com.example.backend.controller;

import com.example.backend.DTO.TaskDTO;
import com.example.backend.model.task.TaskModel;
import com.example.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/task")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

//    @GetMapping(path = "{botId}")
//    public List<TaskModel> getTasks(@PathVariable("botId") Long botId) {
//        return taskService.getTasks(botId);
//    }
//
//    @PostMapping
//    public void addTask(@RequestBody TaskDTO task) {
//        taskService.addTask(task);
//    }
//
//    @DeleteMapping(path = "{taskId}")
//    public void deleteTask(@PathVariable("taskId") Long taskId) {
//        taskService.deleteTask(taskId);
//    }
//
//    @PutMapping
//    public void updateTask(@RequestBody TaskModel newTask) {
//        taskService.updateTask(newTask);
//    }


}
