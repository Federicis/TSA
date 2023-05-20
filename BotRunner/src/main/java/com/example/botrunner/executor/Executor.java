package com.example.botrunner.executor;

import ca.arnah.reddit4j.RedditUserClient;
import ca.arnah.reddit4j.config.RedditClientConfig;
import ca.arnah.reddit4j.config.ScriptClientConfig;
import ca.arnah.reddit4j.objects.app.script.Credentials;
import ca.arnah.reddit4j.objects.app.script.PersonalUseScript;
import ca.arnah.reddit4j.objects.app.script.UserAgent;
import com.example.botrunner.database.models.BotModel;
import com.example.botrunner.database.models.RoutineModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Executor {

    private final RoutineSelector routineSelector;
    public void execute() {
        for (var routine: routineSelector.getRoutinesAwaitingExecution()) {
            RoutineExecutor routineExecutor = new RoutineExecutor(routine, getUserClient(routine.getBot()));
            Thread thread = new Thread(routineExecutor::execute);
            thread.start();
        }
    }
    private RedditUserClient getUserClient(BotModel bot){

        RedditClientConfig redditClientConfig = new ScriptClientConfig(
                new PersonalUseScript(bot.getClientId(), bot.getClientSecret()),
                new UserAgent("TSAA", "1.0", "tsaaaaaaaa"),
                new Credentials(bot.getRedditUsername(), bot.getPassword())
        );
        return new RedditUserClient(redditClientConfig);
    }

}
