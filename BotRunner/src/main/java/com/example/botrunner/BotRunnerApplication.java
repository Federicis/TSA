package com.example.botrunner;

import com.example.botrunner.executor.Executor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BotRunnerApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(BotRunnerApplication.class, args);
        Executor executor = applicationContext.getBean(Executor.class);
        executor.execute();
    }

}
