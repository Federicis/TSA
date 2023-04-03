package com.example.backend.controller;

import com.example.backend.model.BotModel;
import com.example.backend.model.TaskModel;
import com.example.backend.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/bot")
public class BotController {
    private final BotService botService;
    @Autowired
    public BotController(BotService botService) {
        this.botService = botService;
    }

    @GetMapping
    public List<BotModel> getBots() {
        return botService.getBots();
    }
    @GetMapping(path = "{botId}")
    public BotModel getBotById(@PathVariable("botId") Long id) {
        return botService.getBotById(id);
    }
//    @GetMapping(path = "{botName}")
//    public BotModel getBotByName(@PathVariable("botName") String name) {
//        return botService.getBotByName(name);
//    }
    @PostMapping
    public void addNewBot(@RequestBody BotModel bot) {
        botService.addNewBot(bot);
    }
    @DeleteMapping(path = "{botId}")
    public void deleteBot(@PathVariable("botId") Long id) {
        botService.deleteBot(id);
    }
    @PutMapping(path = "{botId}")
    public void updateBot(@PathVariable("botId") Long id, @RequestBody BotModel newBot){
        botService.updateBot(id, newBot);
    }

    @GetMapping(path="{botId}/tasks")
    public List<TaskModel> getTasks(@PathVariable("botId") Long id){
        return botService.getTasks(id);
    }

    @PostMapping(path="{botId}/tasks")
    public void addTask(@PathVariable("botId") Long id, @RequestBody TaskModel task){
        botService.addTask(id, task);
    }

    @DeleteMapping(path="{botId}/{taskId}")
    public void deleteTask(@PathVariable("botId") Long botId, @PathVariable("taskId") Long taskId){
        botService.deleteTask(botId, taskId);
    }

    @PutMapping(path="{botId}/tasks")
    public void updateTask(@PathVariable("botId") Long botId,  @RequestBody TaskModel newTask){
        botService.updateTask(botId, newTask);
    }
}
