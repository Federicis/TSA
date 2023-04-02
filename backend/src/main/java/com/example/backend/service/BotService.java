package com.example.backend.service;

import com.example.backend.model.BotModel;
import com.example.backend.repository.BotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BotService {

    private final BotRepository botRepository;

    @Autowired
    public BotService(BotRepository botRepository) {
        this.botRepository = botRepository;
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

}
