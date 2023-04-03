package com.example.backend.service;

import com.example.backend.model.BotModel;
import com.example.backend.model.TaskModel;
import com.example.backend.repository.BotRepository;
import com.example.backend.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BotService {

    private final BotRepository botRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public BotService(BotRepository botRepository, TaskRepository taskRepository) {
        this.botRepository = botRepository;
        this.taskRepository = taskRepository;
    }

    public List<BotModel> getBots() {
        return botRepository.findAll();
    }

    public BotModel getBotById(Long id) {
        return botRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "bot with id " + id + " does not exist"
        ));
    }

    public BotModel getBotByName(String name) {
        return botRepository.findBotByName(name).orElseThrow(() -> new IllegalStateException(
                "bot with name " + name + " does not exist"
        ));
    }

    public void addNewBot(BotModel bot) {
        botRepository.save(bot);
    }

    public void deleteBot(Long id) {
        if(botRepository.existsById(id))
            botRepository.deleteById(id);
        else throw new IllegalStateException("bot with id " + id + " does not exist");
    }

    @Transactional
    public void updateBot(Long id, BotModel newBot){
        BotModel oldBot = botRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "bot with id " + id + " does not exist"
                ));
        //update each field
        if(newBot.getName() != null && newBot.getName().length() > 0)
            oldBot.setName(newBot.getName());
        oldBot.setDescription(newBot.getDescription());

    }

    public List<TaskModel> getTasks(Long id) {
        if (botRepository.existsById(id)) {
            return taskRepository.findTasksByBotId(id).orElse(List.of());
        } else throw new IllegalStateException("bot with id " + id + " does not exist");
    }

    public void addTask(Long id, TaskModel task) {
        if (botRepository.existsById(id)) {
            task.setBotId(id);
            taskRepository.save(task);
        } else throw new IllegalStateException("bot with id " + id + " does not exist");
    }

    public void deleteTask(Long id, Long taskId) {
        if (botRepository.existsById(id)) {
            TaskModel task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalStateException(
                    "task with id " + taskId + " does not exist"
            ));
            if (Objects.equals(task.getBotId(), id)) {
                taskRepository.deleteById(taskId);
            } else throw new IllegalStateException("task with id " + taskId + " doesn't belong to bot with id " + id);
        } else throw new IllegalStateException("bot with id " + id + " does not exist");
    }

    @Transactional
    public void updateTask(Long id, TaskModel newTask) {
        Long taskId = newTask.getId();
        if (botRepository.existsById(id)) {
            TaskModel oldTask = taskRepository.findById(taskId).orElseThrow(() -> new IllegalStateException(
                    "task with id " + taskId + " does not exist"
            ));
            if (Objects.equals(oldTask.getBotId(), id)) {
                oldTask.setType(newTask.getType());
                oldTask.setParameters(newTask.getParameters());
            } else throw new IllegalStateException("task with id " + taskId + " doesn't belong to bot with id " + id);
        } else throw new IllegalStateException("bot with id " + id + " does not exist");
    }

}
