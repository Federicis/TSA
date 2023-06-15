package com.example.backend.service;

import com.example.backend.DTO.BotDTO;
import com.example.backend.Util.Mapper.BotMapper;
import com.example.backend.model.BotModel;
import com.example.backend.model.UserModel;
import com.example.backend.repository.BotRepository;
import com.example.backend.repository.TaskRepository;
import com.example.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BotService {

    private final BotRepository botRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public List<BotModel> getBots() {
        String username = jwtService.getCurrentUserUsername();
        UserModel user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalStateException("user with username " + username + " does not exist"));
        return botRepository.findByUser(user).orElse(null);
    }

    public BotModel getBotById(Long id) {
        String username = jwtService.getCurrentUserUsername();
        BotModel bot = botRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "bot with id " + id + " does not exist"));
        if (Objects.equals(bot.getUser().getUsername(), username) || jwtService.isAdmin())
            return bot;
        else
            throw new IllegalStateException("bot with id " + id + " does not belong to user " + username);
    }

    public BotModel addNewBot(BotModel bot) {
        String username = jwtService.getCurrentUserUsername();
        UserModel user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalStateException("user with username " + username + " does not exist"));
        bot.setUser(user);
        return botRepository.save(bot);
    }

    public void deleteBot(Long id) {
        String username = jwtService.getCurrentUserUsername();
        BotModel bot = botRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "bot with id " + id + " does not exist"));
        System.out.println(bot.getUser().getUsername());
        if (!Objects.equals(bot.getUser().getUsername(), username) && !jwtService.isAdmin())
            throw new IllegalStateException("this bot does not belong to the user");
        botRepository.deleteById(id);
    }

    @Transactional
    public void updateBot(BotModel newBot) {
        Long id = newBot.getId();
        String username = jwtService.getCurrentUserUsername();
        BotModel oldBot = botRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "bot with id " + id + " does not exist"));
        if (oldBot.getUser().getUsername() != newBot.getUser().getUsername())
            throw new IllegalStateException("cannot change the owner of the bot");
        if (!Objects.equals(oldBot.getUser().getUsername(), jwtService.getCurrentUserUsername())
                && !jwtService.isAdmin())
            throw new IllegalStateException("this bot does not belong to the user");

        // update each field
        botRepository.save(newBot);
    }
}
