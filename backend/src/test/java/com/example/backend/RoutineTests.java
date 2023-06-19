package com.example.backend;

import com.example.backend.model.BotModel;
import com.example.backend.model.RoutineModel;
import com.example.backend.model.UserModel;
import com.example.backend.model.enumeration.Interval;
import com.example.backend.model.enumeration.Role;
import com.example.backend.repository.BotRepository;
import com.example.backend.repository.RoutineRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.JwtService;
import com.example.backend.service.RoutineService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RoutineTests {

    private static UserModel user =
            UserModel.builder()
                    .username("testUser")
                    .password("testPassword")
                    .email("testEmail")
                    .enabled(true)
                    .role(Role.USER)
                    .build();
    private static final BotModel bot =
            BotModel.builder()
                    .name("testBot")
                    .build();
    @MockBean
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BotRepository botRepository;
    @Autowired
    private RoutineRepository routineRepository;
    @Autowired
    private RoutineService routineService;

    @BeforeAll
    void setup() {
        //create a user and a bot
        user = userRepository.save(user);
        bot.setUser(user);
        botRepository.save(bot);
    }

    @Test
    void CreateRoutine() {
        when(jwtService.getCurrentUserUsername()).thenReturn(user.getUsername());
        RoutineModel routine =
                RoutineModel.builder()
                        .name("testRoutine")
                        .bot(bot)
                        .interval(Interval.ASAP)
                        .repeatable(true)
                        .build();
        RoutineModel routineReturnedByService = routineService.AddNewRoutine(routine);
        Optional<RoutineModel> routineFromDb = routineRepository.findById(routineReturnedByService.getId());
        assert (routineFromDb.isPresent());
        assert (routineFromDb.get().getName().equals(routine.getName()));
        assert (routineFromDb.get().getBot().getId().equals(bot.getId()));
        assert (routineFromDb.get().getInterval().equals(routine.getInterval()));
        assert (routineFromDb.get().isRepeatable() == routine.isRepeatable());
    }

}
