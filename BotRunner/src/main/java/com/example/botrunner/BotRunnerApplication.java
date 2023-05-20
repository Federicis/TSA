package com.example.botrunner;

import ca.arnah.reddit4j.RedditUserClient;
import ca.arnah.reddit4j.config.RedditClientConfig;
import ca.arnah.reddit4j.config.ScriptClientConfig;
import ca.arnah.reddit4j.objects.app.script.Credentials;
import ca.arnah.reddit4j.objects.app.script.PersonalUseScript;
import ca.arnah.reddit4j.objects.app.script.UserAgent;
import ca.arnah.reddit4j.objects.reddit.SubredditSort;
import com.example.botrunner.config.ConfigLoader;
import com.example.botrunner.executor.Executor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Properties;

@SpringBootApplication
public class BotRunnerApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(BotRunnerApplication.class, args);
        Executor executor = applicationContext.getBean(Executor.class);
        executor.execute();
    }

}
