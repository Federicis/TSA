package com.example.backend.model.task;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.Data;

import java.util.List;

@Entity
@Data
@DiscriminatorValue("FIND")
public class FindTaskModel extends TaskModel {
    @ElementCollection
    List<String> keywords;

    String subreddit;
}
