package com.example.botrunner.database.services;

import com.example.botrunner.database.models.BotModel;
import com.example.botrunner.database.repositories.BotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BotService {

    private final BotRepository botRepository;

    public List<BotModel> getAllBots() {
        return botRepository.findAll();
    }

    public BotModel getBotById(Long id) {
        return botRepository.findById(id).orElse(null);
    }
}
