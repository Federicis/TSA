package com.example.backend.service;

import com.example.backend.DTO.BotDTO;
import com.example.backend.model.BotModel;
import com.example.backend.model.UserModel;
import com.example.backend.repository.BotRepository;
import com.example.backend.repository.TaskRepository;
import com.example.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BotService {

    private final BotRepository botRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    @Autowired
    public BotService(BotRepository botRepository, TaskRepository taskRepository, UserRepository userRepository, JwtService jwtService) {
        this.botRepository = botRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public List<BotModel> getBots() {
        String username = jwtService.getCurrentUserUsername();
        return botRepository.findByUsername(username).orElse(null);
    }

    public BotModel getBotById(Long id) {
        String username = jwtService.getCurrentUserUsername();
        BotModel bot = botRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "bot with id " + id + " does not exist"
        ));
        if(bot.getUsername().equals(username))
            return bot;
        else throw new IllegalStateException("bot with id " + id + " does not belong to user " + username);
    }

    public BotModel getBotByName(String name) {
        return botRepository.findBotByName(name).orElseThrow(() -> new IllegalStateException(
                "bot with name " + name + " does not exist"
        ));
    }

    public void addNewBot(BotDTO bot) {
        String username = jwtService.getCurrentUserUsername();
        BotModel newBot = BotModel.builder()
                .name(bot.getName())
                .description(bot.getDescription())
                .username(username)
                .redditUsername(bot.getRedditUsername())
                .build();
        botRepository.save(newBot);
    }

    public void deleteBot(Long id) {
        String username = jwtService.getCurrentUserUsername();
        if(botRepository.existsById(id) && botRepository.findById(id).get().getUsername().equals(username))
            botRepository.deleteById(id);
        else throw new IllegalStateException("this bot does not belong to the user");
    }

    @Transactional
    public void updateBot(BotModel newBot){
        Long id = newBot.getId();
        String username = jwtService.getCurrentUserUsername();
        BotModel oldBot = botRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "bot with id " + id + " does not exist"
                ));
        if(!oldBot.getUsername().equals(username))
            throw new IllegalStateException("bot with id " + id + " does not belong to user " + username);
        //update each field
        if(newBot.getName() != null && newBot.getName().length() > 0)
            oldBot.setName(newBot.getName());
        oldBot.setDescription(newBot.getDescription());
        oldBot.setRedditUsername(newBot.getRedditUsername());

    }
}
