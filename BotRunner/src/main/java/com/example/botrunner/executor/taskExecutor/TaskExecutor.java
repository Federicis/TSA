package com.example.botrunner.executor.taskExecutor;

import ca.arnah.reddit4j.RedditUserClient;
import com.example.botrunner.executor.RunningData;

public interface TaskExecutor {
    void execute(RunningData runningData, RedditUserClient redditUserClient);
}
