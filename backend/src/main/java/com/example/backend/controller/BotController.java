package com.example.backend.controller;

import com.example.backend.DTO.BotDTO;
import com.example.backend.model.BotModel;
import com.example.backend.model.TaskModel;
import com.example.backend.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
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

    // @GetMapping(path = "{botName}")
    // public BotModel getBotByName(@PathVariable("botName") String name) {
    // return botService.getBotByName(name);
    // }
    @PostMapping
    public void addNewBot(@RequestBody BotDTO bot) {
        botService.addNewBot(bot);
    }

    @DeleteMapping(path = "{botId}")
    public void deleteBot(@PathVariable("botId") Long id) {
        botService.deleteBot(id);
    }

    @PutMapping
    public void updateBot(@RequestBody BotModel newBot) {
        botService.updateBot(newBot);
    }

}
