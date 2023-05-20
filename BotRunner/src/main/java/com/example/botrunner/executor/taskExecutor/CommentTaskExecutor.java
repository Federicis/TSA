package com.example.botrunner.executor.taskExecutor;

import ca.arnah.reddit4j.RedditUserClient;
import ca.arnah.reddit4j.objects.reddit.Link;
import com.example.botrunner.database.models.task.CommentTaskModel;
import com.example.botrunner.executor.RunningData;

import java.util.List;

public class CommentTaskExecutor implements TaskExecutor{
    private final CommentTaskModel task;

    public CommentTaskExecutor(CommentTaskModel task) {
        this.task = task;
    }

    @Override
    public void execute(RunningData runningData, RedditUserClient redditUserClient) {
        List<Link> post = runningData.getPosts();
        post.forEach(link -> {
           link.getReference(redditUserClient).comment(task.getComment()).execute();
        });
    }
}
