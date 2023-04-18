package com.example.backend.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class CommentTaskModel extends TaskModel{
    String comment;
}
