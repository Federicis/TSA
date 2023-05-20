package com.example.botrunner.executor.taskExecutor;

import ca.arnah.reddit4j.RedditUserClient;
import ca.arnah.reddit4j.objects.reddit.Link;
import ca.arnah.reddit4j.objects.reddit.Listing;
import com.example.botrunner.database.models.task.FindTaskModel;
import com.example.botrunner.executor.RunningData;

public class FindTaskExecutor implements TaskExecutor {

    private final FindTaskModel task;

    public FindTaskExecutor(FindTaskModel task) {
        this.task = task;
    }

    @Override
    public void execute(RunningData runningData, RedditUserClient redditUserClient){
        Listing<Link> lastPosts = redditUserClient.subreddit(task.getSubreddit()).posts().limit(100).build().next();
        lastPosts.forEach(post -> {
            if (task.getKeywords().stream().anyMatch(post.getTitle()::contains))
                runningData.getPosts().add(post);
        });
        for (Link post : lastPosts) {
            System.out.println(post.getTitle());
        }
        System.out.println("Found " + runningData.getPosts().size() + " posts");
        runningData.getPosts().forEach(post -> System.out.println(post.getTitle()));
    }
}
