package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tasks")
public class TaskModel {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private Long id;
    private String type;
    private String parameters;
    private Long botId;

    public List<String> getParametersList() {
        return new ArrayList<String>(List.of(parameters.split(",")));
    }

    public void setParametersList(List<String> parametersList) {
        parameters = String.join(",", parametersList);
    }
}
