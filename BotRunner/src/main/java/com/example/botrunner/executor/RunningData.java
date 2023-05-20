package com.example.botrunner.executor;

import ca.arnah.reddit4j.objects.reddit.Link;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will contain all parameters that can be used by the task executors.
 * It will be used to pass data between the task executors.
 */
@Data
public class RunningData {
    List<Link> posts = new ArrayList<>();
    List<Link> comments = new ArrayList<>();


}
