package com.example.backend.service;

import com.example.backend.model.ExampleModel;
import com.example.backend.repository.ExampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExampleService {

    public final ExampleRepository exampleRepository;
    public List<ExampleModel> findAll() {
        return exampleRepository.findAll();
    }
}
