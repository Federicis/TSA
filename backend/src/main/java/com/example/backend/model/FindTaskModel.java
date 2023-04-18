package com.example.backend.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class FindTaskModel extends TaskModel{
    @ElementCollection
    List<String> keywords;

    String subreddit;
}
