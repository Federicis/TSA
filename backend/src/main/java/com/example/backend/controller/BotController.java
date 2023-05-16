package com.example.backend.controller;

import com.example.backend.DTO.BotDTO;
import com.example.backend.Util.Mapper.BotMapper;
import com.example.backend.model.BotModel;
import com.example.backend.service.BotService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List; 

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/bots")
public class BotController {
    private final BotService botService;
    private final BotMapper botMapper;
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
    public BotDTO addNewBot(@RequestBody BotDTO botDTO) {
        BotModel bot = botMapper.toModel(botDTO);
        return botMapper.toDTO(botService.addNewBot(bot));
    }
    @DeleteMapping(path = "{botId}")
    public void deleteBot(@PathVariable("botId") Long id) {
        botService.deleteBot(id);
    }
    @PutMapping
    public void updateBot(@RequestBody BotDTO newBot){
        BotModel bot = botMapper.toModel(newBot);
        botService.updateBot(bot);
    }

}
