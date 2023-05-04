package com.example.backend.model.task;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("COMMENT")
public class CommentTaskModel extends TaskModel {
    String comment;
}
