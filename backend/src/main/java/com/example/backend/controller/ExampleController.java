package com.example.backend.controller;

import com.example.backend.DTO.ExampleDTO;
import com.example.backend.Util.Mapper.ExampleMapper;
import com.example.backend.model.ExampleModel;
import com.example.backend.service.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/example")
public class ExampleController {

    public final ExampleService exampleService;

    public final ExampleMapper exampleMapper;

    @GetMapping
    public List<ExampleDTO> getAll() {
        List<ExampleModel> exampleModels = exampleService.findAll();
        //use the exampleMapper to convert the exampleModels to exampleDTOs
        return exampleModels.stream().map(exampleMapper::toExampleDTO).collect(Collectors.toList());
    }
}
